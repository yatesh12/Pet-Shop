package com.petshop.catalog.controller;

import com.petshop.catalog.service.CatalogQueryService;
import com.petshop.shared.dto.*;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/catalog")
@RequiredArgsConstructor
public class CatalogQueryController {

    private final CatalogQueryService catalogQueryService;

    @GetMapping("/home")
    public HomePageDto home() {
        return catalogQueryService.getHomePage();
    }

    @GetMapping("/products")
    public PageResponse<ProductDto> products(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Boolean available,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "9") @Min(1) int size,
            @RequestParam(defaultValue = "name") String sort
    ) {
        return catalogQueryService.findProducts(search, category, minPrice, maxPrice, available, page, size, sort);
    }

    @GetMapping("/products/{slug}")
    public ProductDto product(@PathVariable String slug) {
        return catalogQueryService.getProduct(slug);
    }

    @GetMapping("/pets")
    public PageResponse<PetDto> pets(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "8") @Min(1) int size
    ) {
        return catalogQueryService.findPets(search, category, page, size);
    }

    @GetMapping("/pets/{slug}")
    public PetDto pet(@PathVariable String slug) {
        return catalogQueryService.getPet(slug);
    }

    @GetMapping("/services")
    public List<ServiceDto> services() {
        return catalogQueryService.getServices();
    }

    @GetMapping("/blog")
    public PageResponse<BlogPostDto> blog(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size) {
        return catalogQueryService.getBlogPosts(page, size);
    }

    @GetMapping("/blog/{slug}")
    public BlogPostDto blogPost(@PathVariable String slug) {
        return catalogQueryService.getBlogPost(slug);
    }

    @GetMapping("/faq")
    public List<FaqItemDto> faq(@RequestParam(required = false) String search) {
        return catalogQueryService.getFaqs(search);
    }

    @GetMapping("/reviews")
    public List<ReviewDto> reviews() {
        return catalogQueryService.getTestimonials();
    }

    @GetMapping("/promotions")
    public List<PromotionDto> promotions() {
        return catalogQueryService.getPromotions();
    }

    @GetMapping("/team")
    public List<TeamMemberDto> team() {
        return catalogQueryService.getTeamMembers();
    }

    @GetMapping("/categories/products")
    public List<CategoryDto> productCategories() {
        return catalogQueryService.getProductCategories();
    }

    @GetMapping("/categories/pets")
    public List<CategoryDto> petCategories() {
        return catalogQueryService.getPetCategories();
    }
}

