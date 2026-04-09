package com.petshop.shared.dto;

import java.time.LocalDateTime;
import java.util.List;

public record AdminUserDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        boolean enabled,
        List<String> roles,
        LocalDateTime createdAt
) {
}
