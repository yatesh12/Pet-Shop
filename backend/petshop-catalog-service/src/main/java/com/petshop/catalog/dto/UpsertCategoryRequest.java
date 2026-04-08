package com.petshop.catalog.dto;

import jakarta.validation.constraints.NotBlank;

public record UpsertCategoryRequest(
        @NotBlank String name,
        @NotBlank String slug,
        @NotBlank String description
) {
}

