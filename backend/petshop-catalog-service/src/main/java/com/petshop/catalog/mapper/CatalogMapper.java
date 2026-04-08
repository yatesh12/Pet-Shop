package com.petshop.catalog.mapper;

import com.petshop.catalog.entity.*;
import com.petshop.shared.dto.*;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class CatalogMapper {

    public CategoryDto toDto(ProductCategory category) {
        return new CategoryDto(category.getId(), category.getName(), category.getSlug(), category.getDescription());
    }

    public CategoryDto toDto(PetCategory category) {
        return new CategoryDto(category.getId(), category.getName(), category.getSlug(), category.getDescription());
    }

    public ProductDto toDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getSlug(),
                product.getDescription(),
                product.getSupplierName(),
                product.getPrice(),
                product.getStockQuantity(),
                product.isFeatured(),
                product.isActive(),
                product.getImageUrl(),
                toDto(product.getCategory())
        );
    }

    public PetDto toDto(Pet pet) {
        return new PetDto(
                pet.getId(),
                pet.getName(),
                pet.getSlug(),
                pet.getBreed(),
                pet.getAgeInMonths(),
                pet.getGender(),
                pet.getPrice(),
                pet.getSaleType(),
                pet.getDescription(),
                pet.isVaccinated(),
                pet.isAvailable(),
                pet.isFeatured(),
                pet.getImageUrl(),
                toDto(pet.getCategory()),
                pet.getVaccinations().stream().map(PetVaccination::getVaccineName).toList()
        );
    }

    public ServiceDto toDto(CatalogServiceItem service) {
        return new ServiceDto(
                service.getId(),
                service.getName(),
                service.getSlug(),
                service.getCategory(),
                service.getShortDescription(),
                service.getDescription(),
                service.getPrice(),
                service.getDurationMinutes(),
                service.isFeatured(),
                service.isActive(),
                service.getImageUrl()
        );
    }

    public BlogPostDto toDto(BlogPost post) {
        return new BlogPostDto(
                post.getId(),
                post.getTitle(),
                post.getSlug(),
                post.getExcerpt(),
                post.getContent(),
                post.getCategory(),
                post.getTags() == null ? Collections.emptyList() : Arrays.stream(post.getTags().split(",")).map(String::trim).toList(),
                post.getAuthorName(),
                post.getFeaturedImageUrl(),
                post.isPublished(),
                post.getPublishedAt()
        );
    }

    public FaqItemDto toDto(FaqItem item) {
        return new FaqItemDto(item.getId(), item.getQuestion(), item.getAnswer(), item.getCategory(), item.getDisplayOrder(), item.isActive());
    }

    public ReviewDto toDto(Review review) {
        return new ReviewDto(review.getId(), review.getReviewerName(), review.getRating(), review.getTitle(), review.getBody(), review.getSubjectType(), review.getSubjectSlug(), review.isApproved(), review.getCreatedAt());
    }

    public PromotionDto toDto(Promotion promotion) {
        return new PromotionDto(promotion.getId(), promotion.getTitle(), promotion.getSummary(), promotion.getBadge(), promotion.getDiscountText(), promotion.isActive(), promotion.getStartsAt(), promotion.getEndsAt());
    }

    public TeamMemberDto toDto(AdminTeamMember member) {
        return new TeamMemberDto(member.getId(), member.getName(), member.getRoleTitle(), member.getBio(), member.getPhotoUrl(), member.getEmail(), member.getPhone(), member.getDisplayOrder(), member.isActive());
    }

    public <T> PageResponse<T> toPageResponse(Page<T> page) {
        return new PageResponse<>(page.getContent(), page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isFirst(), page.isLast());
    }
}

