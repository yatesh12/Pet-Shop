package com.petshop.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminCategoryForm {
    private String name;
    private String slug;
    private String description;
}
