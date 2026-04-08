package com.petshop.shared.dto;

import java.math.BigDecimal;
import java.util.List;

public record CartDto(
        Long id,
        String ownerReference,
        List<CartItemDto> items,
        BigDecimal subtotal,
        Integer totalItems
) {
}

