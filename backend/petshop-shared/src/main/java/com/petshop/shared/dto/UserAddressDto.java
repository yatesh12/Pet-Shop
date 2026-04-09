package com.petshop.shared.dto;

public record UserAddressDto(
        Long id,
        String label,
        String lineOne,
        String lineTwo,
        String city,
        String state,
        String postalCode,
        String country,
        boolean defaultAddress
) {
}
