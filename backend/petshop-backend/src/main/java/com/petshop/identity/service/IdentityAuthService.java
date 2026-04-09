package com.petshop.identity.service;

import com.petshop.identity.entity.AuthSession;
import com.petshop.identity.entity.IdentityAddress;
import com.petshop.identity.entity.IdentityRole;
import com.petshop.identity.entity.IdentityUser;
import com.petshop.identity.exception.AuthException;
import com.petshop.identity.repository.AuthSessionRepository;
import com.petshop.identity.repository.IdentityAddressRepository;
import com.petshop.identity.repository.IdentityRoleRepository;
import com.petshop.identity.repository.IdentityUserRepository;
import com.petshop.identity.security.AuthenticatedUser;
import com.petshop.shared.dto.*;
import com.petshop.shared.enums.UserRole;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class IdentityAuthService {

    private final IdentityUserRepository userRepository;
    private final IdentityRoleRepository roleRepository;
    private final IdentityAddressRepository addressRepository;
    private final AuthSessionRepository authSessionRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthSessionResponse register(AuthRegisterRequest request) {
        if (userRepository.existsByEmailIgnoreCase(request.email())) {
            throw new AuthException("An account with that email already exists.");
        }
        IdentityUser user = new IdentityUser();
        user.setFirstName(request.firstName().trim());
        user.setLastName(request.lastName().trim());
        user.setEmail(request.email().trim().toLowerCase());
        user.setPhone(request.phone().trim());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.getRoles().add(getRole(UserRole.ROLE_CUSTOMER.name()));
        return openSession(userRepository.save(user));
    }

    public AuthSessionResponse login(AuthLoginRequest request) {
        IdentityUser user = userRepository.findByEmailIgnoreCase(request.email().trim())
                .orElseThrow(() -> new AuthException("Invalid email or password."));
        if (!user.isEnabled()) {
            throw new AuthException("Your account is currently disabled.");
        }
        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new AuthException("Invalid email or password.");
        }
        return openSession(user);
    }

    public void logout(String token) {
        authSessionRepository.deleteByToken(token);
    }

    @Transactional(readOnly = true)
    public AuthenticatedUser authenticate(String token) {
        AuthSession session = authSessionRepository.findByTokenAndExpiresAtAfter(token, LocalDateTime.now())
                .orElseThrow(() -> new AuthException("Session expired or invalid. Please sign in again."));
        IdentityUser user = session.getUser();
        return new AuthenticatedUser(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRoles().stream().map(IdentityRole::getName).sorted().toList(),
                token
        );
    }

    @Transactional(readOnly = true)
    public UserProfileDto currentProfile(Long userId) {
        return toProfile(userRepository.findById(userId).orElseThrow(() -> new AuthException("User not found.")));
    }

    public UserProfileDto updateProfile(Long userId, UserProfileUpdateRequest request) {
        IdentityUser user = userRepository.findById(userId).orElseThrow(() -> new AuthException("User not found."));
        String normalizedEmail = request.email().trim().toLowerCase();
        userRepository.findByEmailIgnoreCase(normalizedEmail)
                .filter(existing -> !existing.getId().equals(userId))
                .ifPresent(existing -> {
                    throw new AuthException("That email address is already in use.");
                });
        user.setFirstName(request.firstName().trim());
        user.setLastName(request.lastName().trim());
        user.setEmail(normalizedEmail);
        user.setPhone(request.phone().trim());
        return toProfile(user);
    }

    public UserAddressDto saveAddress(Long userId, UserAddressRequest request) {
        IdentityUser user = userRepository.findById(userId).orElseThrow(() -> new AuthException("User not found."));
        IdentityAddress address = request.id() == null
                ? new IdentityAddress()
                : addressRepository.findById(request.id()).orElseThrow(() -> new AuthException("Address not found."));
        if (address.getId() != null && !address.getUser().getId().equals(userId)) {
            throw new AuthException("You cannot edit another user's address.");
        }
        address.setUser(user);
        address.setLabel(request.label().trim());
        address.setLineOne(request.lineOne().trim());
        address.setLineTwo(request.lineTwo() == null ? null : request.lineTwo().trim());
        address.setCity(request.city().trim());
        address.setState(request.state().trim());
        address.setPostalCode(request.postalCode().trim());
        address.setCountry(request.country().trim());
        if (request.defaultAddress()) {
            addressRepository.findByUserIdOrderByDefaultAddressDescIdDesc(userId)
                    .forEach(existing -> existing.setDefaultAddress(false));
        }
        address.setDefaultAddress(request.defaultAddress());
        return toAddress(addressRepository.save(address));
    }

    @Transactional(readOnly = true)
    public List<UserAddressDto> addresses(Long userId) {
        return addressRepository.findByUserIdOrderByDefaultAddressDescIdDesc(userId).stream().map(this::toAddress).toList();
    }

    @Transactional(readOnly = true)
    public List<AdminUserDto> users() {
        return userRepository.findAll().stream().map(this::toAdminUser).toList();
    }

    public AdminUserDto assignRole(Long userId, String roleName) {
        IdentityUser user = userRepository.findById(userId).orElseThrow(() -> new AuthException("User not found."));
        user.getRoles().clear();
        user.getRoles().add(getRole(roleName));
        return toAdminUser(user);
    }

    public void ensureBaselineData() {
        for (UserRole role : UserRole.values()) {
            roleRepository.findByName(role.name()).orElseGet(() -> {
                IdentityRole created = new IdentityRole();
                created.setName(role.name());
                return roleRepository.save(created);
            });
        }

        if (!userRepository.existsByEmailIgnoreCase("admin@petshop.local")) {
            IdentityUser admin = new IdentityUser();
            admin.setFirstName("Store");
            admin.setLastName("Admin");
            admin.setEmail("admin@petshop.local");
            admin.setPhone("+1 555-0100");
            admin.setPasswordHash(passwordEncoder.encode("Admin@123"));
            admin.getRoles().add(getRole(UserRole.ROLE_ADMIN.name()));
            userRepository.save(admin);
        }

        if (!userRepository.existsByEmailIgnoreCase("jamie@example.com")) {
            IdentityUser customer = new IdentityUser();
            customer.setFirstName("Jamie");
            customer.setLastName("Carter");
            customer.setEmail("jamie@example.com");
            customer.setPhone("+1 555-0110");
            customer.setPasswordHash(passwordEncoder.encode("Customer@123"));
            customer.getRoles().add(getRole(UserRole.ROLE_CUSTOMER.name()));
            IdentityUser saved = userRepository.save(customer);

            IdentityAddress address = new IdentityAddress();
            address.setUser(saved);
            address.setLabel("Home");
            address.setLineOne("17 Willow Lane");
            address.setCity("Austin");
            address.setState("Texas");
            address.setPostalCode("73301");
            address.setCountry("USA");
            address.setDefaultAddress(true);
            addressRepository.save(address);
        }
    }

    private IdentityRole getRole(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new AuthException("Unsupported role selection."));
    }

    private AuthSessionResponse openSession(IdentityUser user) {
        AuthSession session = new AuthSession();
        session.setToken(UUID.randomUUID().toString().replace("-", "") + UUID.randomUUID().toString().replace("-", ""));
        session.setUser(user);
        session.setExpiresAt(LocalDateTime.now().plusDays(7));
        authSessionRepository.save(session);
        return new AuthSessionResponse(session.getToken(), toProfile(user));
    }

    private UserProfileDto toProfile(IdentityUser user) {
        return new UserProfileDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.isEnabled(),
                user.getRoles().stream().map(IdentityRole::getName).sorted().toList()
        );
    }

    private UserAddressDto toAddress(IdentityAddress address) {
        return new UserAddressDto(
                address.getId(),
                address.getLabel(),
                address.getLineOne(),
                address.getLineTwo(),
                address.getCity(),
                address.getState(),
                address.getPostalCode(),
                address.getCountry(),
                address.isDefaultAddress()
        );
    }

    private AdminUserDto toAdminUser(IdentityUser user) {
        return new AdminUserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.isEnabled(),
                user.getRoles().stream().map(IdentityRole::getName).sorted().toList(),
                user.getCreatedAt()
        );
    }
}
