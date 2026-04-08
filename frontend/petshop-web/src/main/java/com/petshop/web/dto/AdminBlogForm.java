package com.petshop.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminBlogForm {
    private Long id;
    private String title;
    private String slug;
    private String excerpt;
    private String content;
    private String category;
    private String tags;
    private String authorName;
    private String featuredImageUrl;
    private boolean published = true;
}

