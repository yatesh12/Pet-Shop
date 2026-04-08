package com.petshop.shared.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AdoptionRequestPayload(
        Long userId,
        @NotBlank String petSlug,
        @NotBlank String petName,
        @NotBlank String customerName,
        @NotBlank @Email String customerEmail,
        @NotBlank String customerPhone,
        @NotBlank String homeType,
        @NotBlank String experienceLevel,
        String notes
) {
}
