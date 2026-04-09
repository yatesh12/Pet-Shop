package com.petshop.web.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.petshop.shared.enums.UserRole;
import com.petshop.web.dto.RegistrationForm;
import com.petshop.web.entity.AppUser;
import com.petshop.web.entity.Role;
import com.petshop.web.repository.AddressRepository;
import com.petshop.web.repository.RoleRepository;
import com.petshop.web.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserAccountServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private RoleRepository roleRepository;
    @Mock private AddressRepository addressRepository;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserAccountService userAccountService;

    @Test
    void shouldRegisterCustomer() {
        RegistrationForm form = new RegistrationForm();
        form.setFirstName("Jamie");
        form.setLastName("Carter");
        form.setEmail("jamie@example.com");
        form.setPhone("123");
        form.setPassword("Customer@123");

        Role role = new Role();
        role.setName(UserRole.ROLE_CUSTOMER.name());

        when(userRepository.existsByEmailIgnoreCase(form.getEmail())).thenReturn(false);
        when(roleRepository.findByName(UserRole.ROLE_CUSTOMER.name())).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(form.getPassword())).thenReturn("encoded");
        when(userRepository.save(any(AppUser.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AppUser user = userAccountService.register(form);

        assertThat(user.getRoles()).extracting(Role::getName).contains(UserRole.ROLE_CUSTOMER.name());
    }
}
