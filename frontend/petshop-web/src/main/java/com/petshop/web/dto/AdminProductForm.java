package com.petshop.web.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminProductForm {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String supplierName;
    private BigDecimal price;
    private Integer stockQuantity;
    private boolean featured;
    private boolean active = true;
    private String imageUrl;
    private String categorySlug;
}

