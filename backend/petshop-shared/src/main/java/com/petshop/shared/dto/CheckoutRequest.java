package com.petshop.shared.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CheckoutRequest(
        @NotBlank String ownerReference,
        Long userId,
        @NotBlank String customerName,
        @NotBlank @Email String customerEmail,
        @NotBlank String customerPhone,
        @NotBlank String shippingAddress,
        @NotBlank String city,
        @NotBlank String state,
        @NotBlank String postalCode,
        String notes,
        String promoCode,
        @NotBlank String paymentMethod,
        @NotNull Boolean agreeToTerms
) {
}

