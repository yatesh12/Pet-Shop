package com.petshop.catalog.service;

import com.petshop.catalog.dto.*;
import com.petshop.catalog.entity.*;
import com.petshop.catalog.exception.ResourceNotFoundException;
import com.petshop.catalog.mapper.CatalogMapper;
import com.petshop.catalog.repository.*;
import com.petshop.shared.dto.*;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CatalogAdminService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final PetRepository petRepository;
    private final PetCategoryRepository petCategoryRepository;
    private final ServiceRepository serviceRepository;
    private final BlogPostRepository blogPostRepository;
    private final FaqItemRepository faqItemRepository;
    private final CatalogMapper mapper;

    public CatalogAdminSummaryDto getSummary() {
        return new CatalogAdminSummaryDto(productRepository.count(), petRepository.count(), serviceRepository.count(), blogPostRepository.count(), faqItemRepository.count());
    }

    public CategoryDto createProductCategory(UpsertCategoryRequest request) {
        ProductCategory category = new ProductCategory();
        category.setName(request.name());
        category.setSlug(request.slug());
        category.setDescription(request.description());
        return mapper.toDto(productCategoryRepository.save(category));
    }

    public CategoryDto createPetCategory(UpsertCategoryRequest request) {
        PetCategory category = new PetCategory();
        category.setName(request.name());
        category.setSlug(request.slug());
        category.setDescription(request.description());
        return mapper.toDto(petCategoryRepository.save(category));
    }

    public ProductDto saveProduct(Long id, UpsertProductRequest request) {
        Product product = id == null ? new Product() : productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setCategory(productCategoryRepository.findBySlug(request.categorySlug()).orElseThrow(() -> new ResourceNotFoundException("Product category not found")));
        product.setName(request.name());
        product.setSlug(request.slug());
        product.setDescription(request.description());
        product.setSupplierName(request.supplierName());
        product.setPrice(request.price());
        product.setStockQuantity(request.stockQuantity());
        product.setFeatured(request.featured());
        product.setActive(request.active());
        product.setImageUrl(request.imageUrl());
        return mapper.toDto(productRepository.save(product));
    }

    public PetDto savePet(Long id, UpsertPetRequest request) {
        Pet pet = id == null ? new Pet() : petRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pet not found"));
        pet.setCategory(petCategoryRepository.findBySlug(request.categorySlug()).orElseThrow(() -> new ResourceNotFoundException("Pet category not found")));
        pet.setName(request.name());
        pet.setSlug(request.slug());
        pet.setBreed(request.breed());
        pet.setAgeInMonths(request.ageInMonths());
        pet.setGender(request.gender());
        pet.setPrice(request.price());
        pet.setSaleType(request.saleType());
        pet.setDescription(request.description());
        pet.setVaccinated(request.vaccinated());
        pet.setAvailable(request.available());
        pet.setFeatured(request.featured());
        pet.setImageUrl(request.imageUrl());
        pet.getVaccinations().clear();
        if (request.vaccinations() != null) {
            request.vaccinations().forEach(name -> {
                PetVaccination vaccination = new PetVaccination();
                vaccination.setPet(pet);
                vaccination.setVaccineName(name);
                pet.getVaccinations().add(vaccination);
            });
        }
        return mapper.toDto(petRepository.save(pet));
    }

    public ServiceDto saveService(Long id, UpsertServiceRequest request) {
        CatalogServiceItem item = id == null ? new CatalogServiceItem() : serviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Service not found"));
        item.setName(request.name());
        item.setSlug(request.slug());
        item.setCategory(request.category());
        item.setShortDescription(request.shortDescription());
        item.setDescription(request.description());
        item.setPrice(request.price());
        item.setDurationMinutes(request.durationMinutes());
        item.setFeatured(request.featured());
        item.setActive(request.active());
        item.setImageUrl(request.imageUrl());
        return mapper.toDto(serviceRepository.save(item));
    }

    public BlogPostDto saveBlogPost(Long id, UpsertBlogPostRequest request) {
        BlogPost post = id == null ? new BlogPost() : blogPostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog post not found"));
        post.setTitle(request.title());
        post.setSlug(request.slug());
        post.setExcerpt(request.excerpt());
        post.setContent(request.content());
        post.setCategory(request.category());
        post.setTags(request.tags() == null ? "" : String.join(", ", request.tags()));
        post.setAuthorName(request.authorName());
        post.setFeaturedImageUrl(request.featuredImageUrl());
        post.setPublished(request.published());
        post.setPublishedAt(request.published() ? LocalDateTime.now() : null);
        return mapper.toDto(blogPostRepository.save(post));
    }

    public FaqItemDto saveFaq(Long id, UpsertFaqRequest request) {
        FaqItem faqItem = id == null ? new FaqItem() : faqItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("FAQ not found"));
        faqItem.setQuestion(request.question());
        faqItem.setAnswer(request.answer());
        faqItem.setCategory(request.category());
        faqItem.setDisplayOrder(request.displayOrder());
        faqItem.setActive(request.active());
        return mapper.toDto(faqItemRepository.save(faqItem));
    }

    public void deleteProduct(Long id) { productRepository.deleteById(id); }
    public void deletePet(Long id) { petRepository.deleteById(id); }
    public void deleteService(Long id) { serviceRepository.deleteById(id); }
    public void deleteBlog(Long id) { blogPostRepository.deleteById(id); }
    public void deleteFaq(Long id) { faqItemRepository.deleteById(id); }
}
