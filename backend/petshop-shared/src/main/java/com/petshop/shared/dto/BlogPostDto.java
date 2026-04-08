package com.petshop.shared.dto;

import java.time.LocalDateTime;
import java.util.List;

public record BlogPostDto(
        Long id,
        String title,
        String slug,
        String excerpt,
        String content,
        String category,
        List<String> tags,
        String authorName,
        String featuredImageUrl,
        boolean published,
        LocalDateTime publishedAt
) {
}

