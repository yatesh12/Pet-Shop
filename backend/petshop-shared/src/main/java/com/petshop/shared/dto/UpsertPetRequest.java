package com.petshop.shared.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public record UpsertPetRequest(
        @NotBlank String name,
        @NotBlank String slug,
        @NotBlank String breed,
        @NotNull @Min(1) Integer ageInMonths,
        @NotBlank String gender,
        @NotNull @DecimalMin("0.0") BigDecimal price,
        @NotBlank String saleType,
        @NotBlank @Size(max = 1800) String description,
        boolean vaccinated,
        boolean available,
        boolean featured,
        @NotBlank String imageUrl,
        @NotBlank String categorySlug,
        List<@NotBlank String> vaccinations
) {
}
