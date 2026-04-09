package com.petshop.web.security;

import com.petshop.shared.dto.UserProfileDto;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public record CustomUserPrincipal(UserProfileDto user, String token) implements UserDetails {

    public Long id() {
        return user.id();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.roles().stream().map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return user.email();
    }

    @Override
    public boolean isEnabled() {
        return user.enabled();
    }
}

