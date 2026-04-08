package com.petshop.shared.dto;

import com.petshop.shared.enums.ItemType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record WishlistRequest(
        @NotNull Long userId,
        @NotNull ItemType itemType,
        @NotBlank String itemSlug,
        @NotBlank String itemName,
        @NotBlank String imageUrl,
        @NotNull BigDecimal unitPrice
) {
}

