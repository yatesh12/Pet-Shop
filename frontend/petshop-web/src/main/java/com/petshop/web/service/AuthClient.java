package com.petshop.web.service;

import com.petshop.shared.dto.*;
import com.petshop.web.dto.AddressForm;
import com.petshop.web.dto.ProfileForm;
import com.petshop.web.dto.RegistrationForm;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class AuthClient {

    @Qualifier("catalogRestClient")
    private final RestClient backendRestClient;

    public AuthSessionResponse login(String email, String password) {
        return backendRestClient.post()
                .uri("/api/auth/login")
                .body(new AuthLoginRequest(email, password))
                .retrieve()
                .body(AuthSessionResponse.class);
    }

    public AuthSessionResponse register(RegistrationForm form) {
        return backendRestClient.post()
                .uri("/api/auth/register")
                .body(new AuthRegisterRequest(form.getFirstName(), form.getLastName(), form.getEmail(), form.getPhone(), form.getPassword()))
                .retrieve()
                .body(AuthSessionResponse.class);
    }

    public void logout() {
        backendRestClient.post().uri("/api/auth/logout").retrieve().toBodilessEntity();
    }

    public UserProfileDto currentUser() {
        return backendRestClient.get().uri("/api/auth/me").retrieve().body(UserProfileDto.class);
    }

    public UserProfileDto updateProfile(ProfileForm form) {
        return backendRestClient.put()
                .uri("/api/auth/me/profile")
                .body(new UserProfileUpdateRequest(form.getFirstName(), form.getLastName(), form.getEmail(), form.getPhone()))
                .retrieve()
                .body(UserProfileDto.class);
    }

    public List<UserAddressDto> addresses() {
        return backendRestClient.get().uri("/api/auth/me/addresses").retrieve().body(new ParameterizedTypeReference<>() {});
    }

    public UserAddressDto saveAddress(AddressForm form) {
        return backendRestClient.post()
                .uri("/api/auth/me/addresses")
                .body(new UserAddressRequest(form.getId(), form.getLabel(), form.getLineOne(), form.getLineTwo(), form.getCity(), form.getState(), form.getPostalCode(), form.getCountry(), form.isDefaultAddress()))
                .retrieve()
                .body(UserAddressDto.class);
    }

    public List<AdminUserDto> users() {
        return backendRestClient.get().uri("/api/admin/users").retrieve().body(new ParameterizedTypeReference<>() {});
    }

    public AdminUserDto assignRole(Long userId, String roleName) {
        return backendRestClient.put()
                .uri("/api/admin/users/{userId}/role", userId)
                .body(new UserRoleUpdateRequest(roleName))
                .retrieve()
                .body(AdminUserDto.class);
    }
}
