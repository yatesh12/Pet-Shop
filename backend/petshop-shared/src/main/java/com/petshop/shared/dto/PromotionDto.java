package com.petshop.shared.dto;

import java.time.LocalDateTime;

public record PromotionDto(
        Long id,
        String title,
        String summary,
        String badge,
        String discountText,
        boolean active,
        LocalDateTime startsAt,
        LocalDateTime endsAt
) {
}

