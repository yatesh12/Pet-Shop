package com.petshop.web.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminPetForm {
    private Long id;
    private String name;
    private String slug;
    private String breed;
    private Integer ageInMonths;
    private String gender;
    private BigDecimal price;
    private String saleType;
    private String description;
    private boolean vaccinated;
    private boolean available = true;
    private boolean featured;
    private String imageUrl;
    private String categorySlug;
    private String vaccinations;
}

