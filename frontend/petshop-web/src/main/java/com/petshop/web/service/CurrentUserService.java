package com.petshop.web.service;

import com.petshop.shared.dto.UserProfileDto;
import com.petshop.web.security.CustomUserPrincipal;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    public Optional<UserProfileDto> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserPrincipal principal)) {
            return Optional.empty();
        }
        return Optional.of(principal.user());
    }

    public Optional<String> getAccessToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserPrincipal principal)) {
            return Optional.empty();
        }
        return Optional.of(principal.token());
    }

    public Long requireCurrentUserId() {
        return getCurrentUser().map(UserProfileDto::id).orElseThrow(() -> new IllegalStateException("User is not authenticated"));
    }

    public String ownerReference() {
        return "user-" + requireCurrentUserId();
    }
}

