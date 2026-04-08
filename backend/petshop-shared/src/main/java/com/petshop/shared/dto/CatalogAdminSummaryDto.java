package com.petshop.shared.dto;

public record CatalogAdminSummaryDto(
        long productCount,
        long petCount,
        long serviceCount,
        long blogCount,
        long faqCount
) {
}
