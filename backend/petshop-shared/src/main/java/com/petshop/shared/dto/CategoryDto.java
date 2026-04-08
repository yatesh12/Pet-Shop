package com.petshop.shared.dto;

public record CategoryDto(
        Long id,
        String name,
        String slug,
        String description
) {
}

