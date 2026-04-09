package com.petshop.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.shared.dto.ApiErrorResponse;
import com.petshop.shared.dto.AuthSessionResponse;
import com.petshop.web.service.AuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

@Component
@RequiredArgsConstructor
public class BackendAuthenticationProvider implements AuthenticationProvider {

    private final AuthClient authClient;
    private final ObjectMapper objectMapper;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());
        try {
            AuthSessionResponse session = authClient.login(email, password);
            CustomUserPrincipal principal = new CustomUserPrincipal(session.user(), session.token());
            return UsernamePasswordAuthenticationToken.authenticated(principal, null, principal.getAuthorities());
        } catch (RestClientResponseException ex) {
            throw new BadCredentialsException(resolveMessage(ex));
        } catch (Exception ex) {
            throw new BadCredentialsException(ex.getMessage(), ex);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private String resolveMessage(RestClientResponseException ex) {
        try {
            ApiErrorResponse error = objectMapper.readValue(ex.getResponseBodyAsString(), ApiErrorResponse.class);
            return error.message();
        } catch (Exception ignored) {
            return "Invalid email or password.";
        }
    }
}
