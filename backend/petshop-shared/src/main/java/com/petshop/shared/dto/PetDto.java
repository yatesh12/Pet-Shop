package com.petshop.shared.dto;

import java.math.BigDecimal;
import java.util.List;

public record PetDto(
        Long id,
        String name,
        String slug,
        String breed,
        Integer ageInMonths,
        String gender,
        BigDecimal price,
        String saleType,
        String description,
        boolean vaccinated,
        boolean available,
        boolean featured,
        String imageUrl,
        CategoryDto category,
        List<String> vaccinations
) {
}

