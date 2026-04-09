package com.petshop.shared.dto;

import jakarta.validation.constraints.NotBlank;

public record UserAddressRequest(
        Long id,
        @NotBlank String label,
        @NotBlank String lineOne,
        String lineTwo,
        @NotBlank String city,
        @NotBlank String state,
        @NotBlank String postalCode,
        @NotBlank String country,
        boolean defaultAddress
) {
}
