package com.petshop.web.service;

import com.petshop.web.entity.AppUser;
import com.petshop.web.repository.UserRepository;
import com.petshop.web.security.CustomUserPrincipal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;

    public Optional<AppUser> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserPrincipal principal)) {
            return Optional.empty();
        }
        return userRepository.findById(principal.user().getId());
    }

    public Long requireCurrentUserId() {
        return getCurrentUser().map(AppUser::getId).orElseThrow(() -> new IllegalStateException("User is not authenticated"));
    }

    public String ownerReference() {
        return "user-" + requireCurrentUserId();
    }
}

