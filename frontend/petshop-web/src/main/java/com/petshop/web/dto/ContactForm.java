package com.petshop.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactForm {
    @NotBlank private String name;
    @NotBlank @Email private String email;
    @NotBlank private String subject;
    @NotBlank private String message;
    private String type = "CONTACT";
}

