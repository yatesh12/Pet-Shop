package com.petshop.shared.dto;

public record AuthSessionResponse(
        String token,
        UserProfileDto user
) {
}
