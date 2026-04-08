package com.petshop.shared.dto;

import com.petshop.shared.enums.ItemType;
import java.math.BigDecimal;

public record CartItemDto(
        Long id,
        ItemType itemType,
        String itemSlug,
        String itemName,
        String imageUrl,
        BigDecimal unitPrice,
        Integer quantity,
        BigDecimal lineTotal
) {
}

