package com.petshop.catalog.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.petshop.catalog.entity.Product;
import com.petshop.catalog.entity.ProductCategory;
import com.petshop.catalog.mapper.CatalogMapper;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class CatalogMapperTest {

    private final CatalogMapper mapper = new CatalogMapper();

    @Test
    void shouldMapProductToDto() {
        ProductCategory category = new ProductCategory();
        category.setId(1L);
        category.setName("Food");
        category.setSlug("food");
        category.setDescription("Nutrition");

        Product product = new Product();
        product.setId(7L);
        product.setName("Test Product");
        product.setSlug("test-product");
        product.setDescription("Great product");
        product.setSupplierName("Supplier");
        product.setPrice(BigDecimal.TEN);
        product.setStockQuantity(5);
        product.setFeatured(true);
        product.setActive(true);
        product.setImageUrl("/images/test.svg");
        product.setCategory(category);

        assertThat(mapper.toDto(product).category().slug()).isEqualTo("food");
    }
}

