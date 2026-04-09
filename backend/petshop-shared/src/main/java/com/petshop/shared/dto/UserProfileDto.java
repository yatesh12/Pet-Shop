package com.petshop.shared.dto;

import java.util.List;

public record UserProfileDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        boolean enabled,
        List<String> roles
) {
}
