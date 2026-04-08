package com.petshop.shared.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UpsertFaqRequest(
        @NotBlank String question,
        @NotBlank String answer,
        @NotBlank String category,
        @Min(0) int displayOrder,
        boolean active
) {
}
