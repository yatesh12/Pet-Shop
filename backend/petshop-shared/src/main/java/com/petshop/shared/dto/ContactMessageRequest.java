package com.petshop.shared.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ContactMessageRequest(
        Long userId,
        @NotBlank String type,
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank String subject,
        @NotBlank String message
) {
}

