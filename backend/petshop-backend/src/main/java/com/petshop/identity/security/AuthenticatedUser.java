package com.petshop.identity.security;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public record AuthenticatedUser(
        Long id,
        String email,
        String firstName,
        String lastName,
        List<String> roles,
        String token
) {
    public Collection<? extends GrantedAuthority> authorities() {
        return roles.stream().map(SimpleGrantedAuthority::new).toList();
    }

    public boolean hasRole(String roleName) {
        return roles.contains(roleName);
    }
}
