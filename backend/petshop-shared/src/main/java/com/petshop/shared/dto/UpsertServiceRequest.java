package com.petshop.shared.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record UpsertServiceRequest(
        @NotBlank String name,
        @NotBlank String slug,
        @NotBlank String category,
        @NotBlank String shortDescription,
        @NotBlank @Size(max = 1400) String description,
        @NotNull @DecimalMin("0.0") BigDecimal price,
        @NotNull @Min(15) Integer durationMinutes,
        boolean featured,
        boolean active,
        @NotBlank String imageUrl
) {
}
