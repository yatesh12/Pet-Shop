package com.petshop.shared.dto;

public record FaqItemDto(
        Long id,
        String question,
        String answer,
        String category,
        Integer displayOrder,
        boolean active
) {
}

