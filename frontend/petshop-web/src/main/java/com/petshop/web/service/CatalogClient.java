package com.petshop.web.service;

import com.petshop.shared.dto.*;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class CatalogClient {

    @Qualifier("catalogRestClient")
    private final RestClient catalogRestClient;

    public HomePageDto home() {
        return catalogRestClient.get().uri("/api/catalog/home").retrieve().body(HomePageDto.class);
    }

    public PageResponse<ProductDto> products(String search, String category, BigDecimal minPrice, BigDecimal maxPrice, Boolean available, int page, int size, String sort) {
        String uri = UriComponentsBuilder.fromPath("/api/catalog/products")
                .queryParamIfPresent("search", java.util.Optional.ofNullable(search))
                .queryParamIfPresent("category", java.util.Optional.ofNullable(category))
                .queryParamIfPresent("minPrice", java.util.Optional.ofNullable(minPrice))
                .queryParamIfPresent("maxPrice", java.util.Optional.ofNullable(maxPrice))
                .queryParamIfPresent("available", java.util.Optional.ofNullable(available))
                .queryParam("page", page)
                .queryParam("size", size)
                .queryParam("sort", sort)
                .build()
                .toUriString();
        return catalogRestClient.get().uri(uri).retrieve().body(new ParameterizedTypeReference<>() {});
    }

    public List<CategoryDto> productCategories() {
        return catalogRestClient.get().uri("/api/catalog/categories/products").retrieve().body(new ParameterizedTypeReference<>() {});
    }

    public PageResponse<PetDto> pets(String search, String category, int page, int size) {
        String uri = UriComponentsBuilder.fromPath("/api/catalog/pets")
                .queryParamIfPresent("search", java.util.Optional.ofNullable(search))
                .queryParamIfPresent("category", java.util.Optional.ofNullable(category))
                .queryParam("page", page)
                .queryParam("size", size)
                .build()
                .toUriString();
        return catalogRestClient.get().uri(uri).retrieve().body(new ParameterizedTypeReference<>() {});
    }

    public List<CategoryDto> petCategories() {
        return catalogRestClient.get().uri("/api/catalog/categories/pets").retrieve().body(new ParameterizedTypeReference<>() {});
    }

    public PetDto pet(String slug) {
        return catalogRestClient.get().uri("/api/catalog/pets/{slug}", slug).retrieve().body(PetDto.class);
    }

    public List<ServiceDto> services() {
        return catalogRestClient.get().uri("/api/catalog/services").retrieve().body(new ParameterizedTypeReference<>() {});
    }

    public List<TeamMemberDto> team() {
        return catalogRestClient.get().uri("/api/catalog/team").retrieve().body(new ParameterizedTypeReference<>() {});
    }

    public List<FaqItemDto> faqs(String search) {
        String uri = UriComponentsBuilder.fromPath("/api/catalog/faq").queryParamIfPresent("search", java.util.Optional.ofNullable(search)).build().toUriString();
        return catalogRestClient.get().uri(uri).retrieve().body(new ParameterizedTypeReference<>() {});
    }

    public PageResponse<BlogPostDto> blog(int page, int size) {
        return catalogRestClient.get().uri("/api/catalog/blog?page={page}&size={size}", page, size).retrieve().body(new ParameterizedTypeReference<>() {});
    }

    public BlogPostDto blogPost(String slug) {
        return catalogRestClient.get().uri("/api/catalog/blog/{slug}", slug).retrieve().body(BlogPostDto.class);
    }

    public List<PromotionDto> promotions() {
        return catalogRestClient.get().uri("/api/catalog/promotions").retrieve().body(new ParameterizedTypeReference<>() {});
    }

    public List<ReviewDto> reviews() {
        return catalogRestClient.get().uri("/api/catalog/reviews").retrieve().body(new ParameterizedTypeReference<>() {});
    }

    public CatalogAdminSummaryDto adminSummary() {
        return catalogRestClient.get().uri("/api/catalog/admin/summary").retrieve().body(CatalogAdminSummaryDto.class);
    }

    public void saveProduct(Long id, UpsertProductRequest request) {
        if (id == null) {
            catalogRestClient.post().uri("/api/catalog/admin/products").body(request).retrieve().toBodilessEntity();
        } else {
            catalogRestClient.put().uri("/api/catalog/admin/products/{id}", id).body(request).retrieve().toBodilessEntity();
        }
    }

    public void deleteProduct(Long id) {
        catalogRestClient.delete().uri("/api/catalog/admin/products/{id}", id).retrieve().toBodilessEntity();
    }

    public void savePet(Long id, UpsertPetRequest request) {
        if (id == null) {
            catalogRestClient.post().uri("/api/catalog/admin/pets").body(request).retrieve().toBodilessEntity();
        } else {
            catalogRestClient.put().uri("/api/catalog/admin/pets/{id}", id).body(request).retrieve().toBodilessEntity();
        }
    }

    public void deletePet(Long id) {
        catalogRestClient.delete().uri("/api/catalog/admin/pets/{id}", id).retrieve().toBodilessEntity();
    }

    public void saveService(Long id, UpsertServiceRequest request) {
        if (id == null) {
            catalogRestClient.post().uri("/api/catalog/admin/services").body(request).retrieve().toBodilessEntity();
        } else {
            catalogRestClient.put().uri("/api/catalog/admin/services/{id}", id).body(request).retrieve().toBodilessEntity();
        }
    }

    public void deleteService(Long id) {
        catalogRestClient.delete().uri("/api/catalog/admin/services/{id}", id).retrieve().toBodilessEntity();
    }

    public void saveBlog(Long id, UpsertBlogPostRequest request) {
        if (id == null) {
            catalogRestClient.post().uri("/api/catalog/admin/blog").body(request).retrieve().toBodilessEntity();
        } else {
            catalogRestClient.put().uri("/api/catalog/admin/blog/{id}", id).body(request).retrieve().toBodilessEntity();
        }
    }

    public void deleteBlog(Long id) {
        catalogRestClient.delete().uri("/api/catalog/admin/blog/{id}", id).retrieve().toBodilessEntity();
    }

    public void saveFaq(Long id, UpsertFaqRequest request) {
        if (id == null) {
            catalogRestClient.post().uri("/api/catalog/admin/faq").body(request).retrieve().toBodilessEntity();
        } else {
            catalogRestClient.put().uri("/api/catalog/admin/faq/{id}", id).body(request).retrieve().toBodilessEntity();
        }
    }

    public void deleteFaq(Long id) {
        catalogRestClient.delete().uri("/api/catalog/admin/faq/{id}", id).retrieve().toBodilessEntity();
    }

    public void createProductCategory(UpsertCategoryRequest request) {
        catalogRestClient.post().uri("/api/catalog/admin/categories/products").body(request).retrieve().toBodilessEntity();
    }

    public void createPetCategory(UpsertCategoryRequest request) {
        catalogRestClient.post().uri("/api/catalog/admin/categories/pets").body(request).retrieve().toBodilessEntity();
    }
}
