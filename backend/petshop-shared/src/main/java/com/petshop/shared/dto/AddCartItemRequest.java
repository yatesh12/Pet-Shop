package com.petshop.shared.dto;

import com.petshop.shared.enums.ItemType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record AddCartItemRequest(
        @NotNull ItemType itemType,
        @NotBlank String itemSlug,
        @NotBlank String itemName,
        @NotBlank String imageUrl,
        @NotNull BigDecimal unitPrice,
        @NotNull @Min(1) Integer quantity
) {
}

