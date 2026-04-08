package com.petshop.shared.dto;

import java.math.BigDecimal;

public record ServiceDto(
        Long id,
        String name,
        String slug,
        String category,
        String shortDescription,
        String description,
        BigDecimal price,
        Integer durationMinutes,
        boolean featured,
        boolean active,
        String imageUrl
) {
}

