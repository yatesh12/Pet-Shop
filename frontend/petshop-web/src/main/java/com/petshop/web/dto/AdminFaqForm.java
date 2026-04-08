package com.petshop.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminFaqForm {
    private Long id;
    private String question;
    private String answer;
    private String category;
    private int displayOrder;
    private boolean active = true;
}

