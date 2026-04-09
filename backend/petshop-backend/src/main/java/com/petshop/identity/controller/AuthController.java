package com.petshop.identity.controller;

import com.petshop.identity.security.AuthenticatedUser;
import com.petshop.identity.service.CurrentIdentityService;
import com.petshop.identity.service.IdentityAuthService;
import com.petshop.shared.dto.*;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IdentityAuthService identityAuthService;
    private final CurrentIdentityService currentIdentityService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthSessionResponse register(@Valid @RequestBody AuthRegisterRequest request) {
        return identityAuthService.register(request);
    }

    @PostMapping("/login")
    public AuthSessionResponse login(@Valid @RequestBody AuthLoginRequest request) {
        return identityAuthService.login(request);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        identityAuthService.logout(authHeader.substring(7));
    }

    @GetMapping("/me")
    public UserProfileDto me() {
        AuthenticatedUser user = currentIdentityService.requireCurrentUser();
        return identityAuthService.currentProfile(user.id());
    }

    @PutMapping("/me/profile")
    public UserProfileDto updateProfile(@Valid @RequestBody UserProfileUpdateRequest request) {
        AuthenticatedUser user = currentIdentityService.requireCurrentUser();
        return identityAuthService.updateProfile(user.id(), request);
    }

    @GetMapping("/me/addresses")
    public List<UserAddressDto> addresses() {
        AuthenticatedUser user = currentIdentityService.requireCurrentUser();
        return identityAuthService.addresses(user.id());
    }

    @PostMapping("/me/addresses")
    @ResponseStatus(HttpStatus.CREATED)
    public UserAddressDto saveAddress(@Valid @RequestBody UserAddressRequest request) {
        AuthenticatedUser user = currentIdentityService.requireCurrentUser();
        return identityAuthService.saveAddress(user.id(), request);
    }
}
