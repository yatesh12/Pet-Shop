package com.petshop.web.security;

import com.petshop.shared.dto.UserProfileDto;
import com.petshop.web.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmailIgnoreCase(username)
                .map(user -> new CustomUserPrincipal(
                        new UserProfileDto(
                                user.getId(),
                                user.getFirstName(),
                                user.getLastName(),
                                user.getEmail(),
                                user.getPhone(),
                                user.isEnabled(),
                                user.getRoles().stream().map(role -> role.getName()).toList()
                        ),
                        ""
                ))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}

