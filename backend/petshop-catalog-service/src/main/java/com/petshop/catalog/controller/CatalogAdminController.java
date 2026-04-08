package com.petshop.catalog.controller;

import com.petshop.catalog.dto.*;
import com.petshop.catalog.service.CatalogAdminService;
import com.petshop.shared.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/catalog/admin")
@RequiredArgsConstructor
public class CatalogAdminController {

    private final CatalogAdminService catalogAdminService;

    @GetMapping("/summary")
    public CatalogAdminSummaryDto summary() {
        return catalogAdminService.getSummary();
    }

    @PostMapping("/categories/products")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createProductCategory(@Valid @RequestBody UpsertCategoryRequest request) {
        return catalogAdminService.createProductCategory(request);
    }

    @PostMapping("/categories/pets")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createPetCategory(@Valid @RequestBody UpsertCategoryRequest request) {
        return catalogAdminService.createPetCategory(request);
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createProduct(@Valid @RequestBody UpsertProductRequest request) {
        return catalogAdminService.saveProduct(null, request);
    }

    @PutMapping("/products/{id}")
    public ProductDto updateProduct(@PathVariable Long id, @Valid @RequestBody UpsertProductRequest request) {
        return catalogAdminService.saveProduct(id, request);
    }

    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        catalogAdminService.deleteProduct(id);
    }

    @PostMapping("/pets")
    @ResponseStatus(HttpStatus.CREATED)
    public PetDto createPet(@Valid @RequestBody UpsertPetRequest request) {
        return catalogAdminService.savePet(null, request);
    }

    @PutMapping("/pets/{id}")
    public PetDto updatePet(@PathVariable Long id, @Valid @RequestBody UpsertPetRequest request) {
        return catalogAdminService.savePet(id, request);
    }

    @DeleteMapping("/pets/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePet(@PathVariable Long id) {
        catalogAdminService.deletePet(id);
    }

    @PostMapping("/services")
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceDto createService(@Valid @RequestBody UpsertServiceRequest request) {
        return catalogAdminService.saveService(null, request);
    }

    @PutMapping("/services/{id}")
    public ServiceDto updateService(@PathVariable Long id, @Valid @RequestBody UpsertServiceRequest request) {
        return catalogAdminService.saveService(id, request);
    }

    @DeleteMapping("/services/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteService(@PathVariable Long id) {
        catalogAdminService.deleteService(id);
    }

    @PostMapping("/blog")
    @ResponseStatus(HttpStatus.CREATED)
    public BlogPostDto createBlog(@Valid @RequestBody UpsertBlogPostRequest request) {
        return catalogAdminService.saveBlogPost(null, request);
    }

    @PutMapping("/blog/{id}")
    public BlogPostDto updateBlog(@PathVariable Long id, @Valid @RequestBody UpsertBlogPostRequest request) {
        return catalogAdminService.saveBlogPost(id, request);
    }

    @DeleteMapping("/blog/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBlog(@PathVariable Long id) {
        catalogAdminService.deleteBlog(id);
    }

    @PostMapping("/faq")
    @ResponseStatus(HttpStatus.CREATED)
    public FaqItemDto createFaq(@Valid @RequestBody UpsertFaqRequest request) {
        return catalogAdminService.saveFaq(null, request);
    }

    @PutMapping("/faq/{id}")
    public FaqItemDto updateFaq(@PathVariable Long id, @Valid @RequestBody UpsertFaqRequest request) {
        return catalogAdminService.saveFaq(id, request);
    }

    @DeleteMapping("/faq/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFaq(@PathVariable Long id) {
        catalogAdminService.deleteFaq(id);
    }
}

