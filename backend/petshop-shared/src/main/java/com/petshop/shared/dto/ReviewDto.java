package com.petshop.shared.dto;

import java.time.LocalDateTime;

public record ReviewDto(
        Long id,
        String reviewerName,
        Integer rating,
        String title,
        String body,
        String subjectType,
        String subjectSlug,
        boolean approved,
        LocalDateTime createdAt
) {
}

