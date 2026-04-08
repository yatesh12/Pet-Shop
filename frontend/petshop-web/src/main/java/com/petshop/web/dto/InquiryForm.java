package com.petshop.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryForm {
    @NotBlank private String customerName;
    @NotBlank @Email private String customerEmail;
    private String phone;
    @NotBlank private String message;
}

