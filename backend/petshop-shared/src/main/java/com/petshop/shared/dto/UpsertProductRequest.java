package com.petshop.shared.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record UpsertProductRequest(
        @NotBlank String name,
        @NotBlank String slug,
        @NotBlank @Size(max = 1500) String description,
        @NotBlank String supplierName,
        @NotNull @DecimalMin("0.0") BigDecimal price,
        @NotNull @Min(0) Integer stockQuantity,
        boolean featured,
        boolean active,
        @NotBlank String imageUrl,
        @NotBlank String categorySlug
) {
}
