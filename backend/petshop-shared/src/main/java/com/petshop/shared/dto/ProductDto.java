package com.petshop.shared.dto;

import java.math.BigDecimal;

public record ProductDto(
        Long id,
        String name,
        String slug,
        String description,
        String supplierName,
        BigDecimal price,
        Integer stockQuantity,
        boolean featured,
        boolean active,
        String imageUrl,
        CategoryDto category
) {
}

