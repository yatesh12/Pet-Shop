package com.petshop.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdoptionForm {
    @NotBlank private String customerName;
    @NotBlank @Email private String customerEmail;
    @NotBlank private String customerPhone;
    @NotBlank private String homeType;
    @NotBlank private String experienceLevel;
    private String notes;
}

