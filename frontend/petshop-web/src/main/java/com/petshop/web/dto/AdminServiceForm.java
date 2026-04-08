package com.petshop.web.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminServiceForm {
    private Long id;
    private String name;
    private String slug;
    private String category;
    private String shortDescription;
    private String description;
    private BigDecimal price;
    private Integer durationMinutes;
    private boolean featured;
    private boolean active = true;
    private String imageUrl;
}

