package com.petshop.shared.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public record UpsertBlogPostRequest(
        @NotBlank String title,
        @NotBlank String slug,
        @NotBlank @Size(max = 320) String excerpt,
        @NotBlank @Size(max = 12000) String content,
        @NotBlank String category,
        List<@NotBlank String> tags,
        @NotBlank String authorName,
        @NotBlank String featuredImageUrl,
        boolean published
) {
}
