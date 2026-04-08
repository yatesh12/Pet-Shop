package com.petshop.shared.dto;

import com.petshop.shared.enums.ItemType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record WishlistItemDto(
        Long id,
        Long userId,
        ItemType itemType,
        String itemSlug,
        String itemName,
        String imageUrl,
        BigDecimal unitPrice,
        LocalDateTime createdAt
) {
}

