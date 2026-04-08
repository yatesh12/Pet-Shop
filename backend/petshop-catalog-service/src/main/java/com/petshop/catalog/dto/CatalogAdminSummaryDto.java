package com.petshop.catalog.dto;

public record CatalogAdminSummaryDto(
        long productCount,
        long petCount,
        long serviceCount,
        long blogCount,
        long faqCount
) {
}

