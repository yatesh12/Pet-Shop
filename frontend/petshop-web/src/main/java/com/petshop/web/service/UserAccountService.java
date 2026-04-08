package com.petshop.web.service;

import com.petshop.shared.enums.UserRole;
import com.petshop.web.dto.AddressForm;
import com.petshop.web.dto.ProfileForm;
import com.petshop.web.dto.RegistrationForm;
import com.petshop.web.entity.Address;
import com.petshop.web.entity.AppUser;
import com.petshop.web.entity.Role;
import com.petshop.web.repository.AddressRepository;
import com.petshop.web.repository.RoleRepository;
import com.petshop.web.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserAccountService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUser register(RegistrationForm form) {
        if (userRepository.existsByEmailIgnoreCase(form.getEmail())) {
            throw new IllegalArgumentException("An account with that email already exists");
        }
        AppUser user = new AppUser();
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setEmail(form.getEmail());
        user.setPhone(form.getPhone());
        user.setPasswordHash(passwordEncoder.encode(form.getPassword()));
        user.getRoles().add(roleRepository.findByName(UserRole.ROLE_CUSTOMER.name()).orElseThrow());
        return userRepository.save(user);
    }

    public AppUser updateProfile(Long userId, ProfileForm form) {
        AppUser user = userRepository.findById(userId).orElseThrow();
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setEmail(form.getEmail());
        user.setPhone(form.getPhone());
        return user;
    }

    public Address saveAddress(Long userId, AddressForm form) {
        AppUser user = userRepository.findById(userId).orElseThrow();
        Address address = form.getId() == null ? new Address() : addressRepository.findById(form.getId()).orElseThrow();
        address.setUser(user);
        address.setLabel(form.getLabel());
        address.setLineOne(form.getLineOne());
        address.setLineTwo(form.getLineTwo());
        address.setCity(form.getCity());
        address.setState(form.getState());
        address.setPostalCode(form.getPostalCode());
        address.setCountry(form.getCountry());
        if (form.isDefaultAddress()) {
            addressRepository.findByUserIdOrderByDefaultAddressDescIdDesc(userId).forEach(existing -> existing.setDefaultAddress(false));
        }
        address.setDefaultAddress(form.isDefaultAddress());
        return addressRepository.save(address);
    }

    @Transactional(readOnly = true)
    public List<Address> addresses(Long userId) {
        return addressRepository.findByUserIdOrderByDefaultAddressDescIdDesc(userId);
    }

    @Transactional(readOnly = true)
    public List<AppUser> users() {
        return userRepository.findAll();
    }

    public void assignRole(Long userId, String roleName) {
        AppUser user = userRepository.findById(userId).orElseThrow();
        Role role = roleRepository.findByName(roleName).orElseThrow();
        user.getRoles().clear();
        user.getRoles().add(role);
    }

    public void ensureBaselineData() {
        for (UserRole role : UserRole.values()) {
            roleRepository.findByName(role.name()).orElseGet(() -> {
                Role entity = new Role();
                entity.setName(role.name());
                return roleRepository.save(entity);
            });
        }
        if (!userRepository.existsByEmailIgnoreCase("admin@petshop.local")) {
            RegistrationForm adminForm = new RegistrationForm();
            adminForm.setFirstName("Store");
            adminForm.setLastName("Admin");
            adminForm.setEmail("admin@petshop.local");
            adminForm.setPhone("+1 555-0100");
            adminForm.setPassword("Admin@123");
            AppUser admin = register(adminForm);
            admin.getRoles().clear();
            admin.getRoles().add(roleRepository.findByName(UserRole.ROLE_ADMIN.name()).orElseThrow());
        }
        if (!userRepository.existsByEmailIgnoreCase("jamie@example.com")) {
            RegistrationForm customerForm = new RegistrationForm();
            customerForm.setFirstName("Jamie");
            customerForm.setLastName("Carter");
            customerForm.setEmail("jamie@example.com");
            customerForm.setPhone("+1 555-0110");
            customerForm.setPassword("Customer@123");
            AppUser user = register(customerForm);
            AddressForm addressForm = new AddressForm();
            addressForm.setLabel("Home");
            addressForm.setLineOne("17 Willow Lane");
            addressForm.setCity("Austin");
            addressForm.setState("Texas");
            addressForm.setPostalCode("73301");
            addressForm.setCountry("USA");
            addressForm.setDefaultAddress(true);
            saveAddress(user.getId(), addressForm);
        }
    }
}

