package com.petshop.shared.dto;

import java.util.List;

public record HomePageDto(
        List<ProductDto> featuredProducts,
        List<PetDto> featuredPets,
        List<ServiceDto> featuredServices,
        List<PromotionDto> promotions,
        List<ReviewDto> testimonials,
        List<BlogPostDto> latestPosts
) {
}

