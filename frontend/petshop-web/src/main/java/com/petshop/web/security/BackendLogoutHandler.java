package com.petshop.web.security;

import com.petshop.web.service.AuthClient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BackendLogoutHandler implements LogoutHandler {

    private final AuthClient authClient;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserPrincipal) {
            try {
                authClient.logout();
            } catch (Exception ignored) {
            }
        }
    }
}
