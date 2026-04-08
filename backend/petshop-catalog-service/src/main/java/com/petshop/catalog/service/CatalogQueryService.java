package com.petshop.catalog.service;

import com.petshop.catalog.entity.CatalogServiceItem;
import com.petshop.catalog.entity.Pet;
import com.petshop.catalog.entity.Product;
import com.petshop.catalog.exception.ResourceNotFoundException;
import com.petshop.catalog.mapper.CatalogMapper;
import com.petshop.catalog.repository.*;
import com.petshop.shared.dto.*;
import jakarta.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CatalogQueryService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final PetRepository petRepository;
    private final PetCategoryRepository petCategoryRepository;
    private final ServiceRepository serviceRepository;
    private final BlogPostRepository blogPostRepository;
    private final FaqItemRepository faqItemRepository;
    private final ReviewRepository reviewRepository;
    private final PromotionRepository promotionRepository;
    private final AdminTeamMemberRepository teamMemberRepository;
    private final CatalogMapper mapper;

    public HomePageDto getHomePage() {
        Pageable featuredPage = PageRequest.of(0, 4, Sort.by(Sort.Direction.DESC, "featured").and(Sort.by("name")));
        List<ProductDto> featuredProducts = productRepository.findAll((root, query, cb) ->
                        cb.and(cb.isTrue(root.get("active")), cb.isTrue(root.get("featured"))), featuredPage)
                .map(mapper::toDto)
                .getContent();
        List<PetDto> featuredPets = petRepository.findAll((root, query, cb) ->
                        cb.and(cb.isTrue(root.get("available")), cb.isTrue(root.get("featured"))), featuredPage)
                .map(mapper::toDto)
                .getContent();
        List<ServiceDto> featuredServices = serviceRepository.findByActiveTrueOrderByFeaturedDescNameAsc().stream()
                .filter(CatalogServiceItem::isFeatured)
                .limit(4)
                .map(mapper::toDto)
                .toList();
        LocalDateTime now = LocalDateTime.now();
        return new HomePageDto(
                featuredProducts,
                featuredPets,
                featuredServices,
                promotionRepository.findTop6ByActiveTrueAndStartsAtBeforeAndEndsAtAfterOrderByStartsAtDesc(now, now).stream().map(mapper::toDto).toList(),
                reviewRepository.findTop6ByApprovedTrueOrderByCreatedAtDesc().stream().map(mapper::toDto).toList(),
                blogPostRepository.findByPublishedTrue(PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "publishedAt"))).stream().map(mapper::toDto).toList()
        );
    }

    public PageResponse<ProductDto> findProducts(String search, String category, BigDecimal minPrice, BigDecimal maxPrice, Boolean available, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, (sortBy == null || sortBy.isBlank()) ? "name" : sortBy));
        Specification<Product> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.isTrue(root.get("active")));
            if (search != null && !search.isBlank()) {
                predicates.add(cb.or(cb.like(cb.lower(root.get("name")), "%" + search.toLowerCase() + "%"),
                        cb.like(cb.lower(root.get("description")), "%" + search.toLowerCase() + "%")));
            }
            if (category != null && !category.isBlank()) {
                predicates.add(cb.equal(root.join("category").get("slug"), category));
            }
            if (minPrice != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), minPrice));
            }
            if (maxPrice != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), maxPrice));
            }
            if (Boolean.TRUE.equals(available)) {
                predicates.add(cb.greaterThan(root.get("stockQuantity"), 0));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return mapper.toPageResponse(productRepository.findAll(specification, pageable).map(mapper::toDto));
    }

    public ProductDto getProduct(String slug) {
        return mapper.toDto(productRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found for slug " + slug)));
    }

    public PageResponse<PetDto> findPets(String search, String category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "featured").and(Sort.by("name")));
        Specification<Pet> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.isTrue(root.get("available")));
            if (search != null && !search.isBlank()) {
                predicates.add(cb.or(cb.like(cb.lower(root.get("name")), "%" + search.toLowerCase() + "%"),
                        cb.like(cb.lower(root.get("breed")), "%" + search.toLowerCase() + "%")));
            }
            if (category != null && !category.isBlank()) {
                predicates.add(cb.equal(root.join("category").get("slug"), category));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return mapper.toPageResponse(petRepository.findAll(specification, pageable).map(mapper::toDto));
    }

    public PetDto getPet(String slug) {
        return mapper.toDto(petRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found for slug " + slug)));
    }

    public List<ServiceDto> getServices() {
        return serviceRepository.findByActiveTrueOrderByFeaturedDescNameAsc().stream().map(mapper::toDto).toList();
    }

    public PageResponse<BlogPostDto> getBlogPosts(int page, int size) {
        return mapper.toPageResponse(blogPostRepository.findByPublishedTrue(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "publishedAt"))).map(mapper::toDto));
    }

    public BlogPostDto getBlogPost(String slug) {
        return mapper.toDto(blogPostRepository.findBySlugAndPublishedTrue(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Blog post not found for slug " + slug)));
    }

    public List<FaqItemDto> getFaqs(String search) {
        if (search == null || search.isBlank()) {
            return faqItemRepository.findByActiveTrueOrderByDisplayOrderAsc().stream().map(mapper::toDto).toList();
        }
        return faqItemRepository.findByActiveTrueAndQuestionContainingIgnoreCaseOrderByDisplayOrderAsc(search).stream().map(mapper::toDto).toList();
    }

    public List<ReviewDto> getTestimonials() {
        return reviewRepository.findTop6ByApprovedTrueOrderByCreatedAtDesc().stream().map(mapper::toDto).toList();
    }

    public List<PromotionDto> getPromotions() {
        LocalDateTime now = LocalDateTime.now();
        return promotionRepository.findTop6ByActiveTrueAndStartsAtBeforeAndEndsAtAfterOrderByStartsAtDesc(now, now).stream().map(mapper::toDto).toList();
    }

    public List<TeamMemberDto> getTeamMembers() {
        return teamMemberRepository.findByActiveTrueOrderByDisplayOrderAsc().stream().map(mapper::toDto).toList();
    }

    public List<CategoryDto> getProductCategories() {
        return productCategoryRepository.findAll(Sort.by("name")).stream().map(mapper::toDto).toList();
    }

    public List<CategoryDto> getPetCategories() {
        return petCategoryRepository.findAll(Sort.by("name")).stream().map(mapper::toDto).toList();
    }
}

