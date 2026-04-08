package com.petshop.shared.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record InquiryRequest(
        Long userId,
        @NotBlank String itemSlug,
        @NotBlank String itemType,
        @NotBlank String customerName,
        @NotBlank @Email String customerEmail,
        String phone,
        @NotBlank String message
) {
}

