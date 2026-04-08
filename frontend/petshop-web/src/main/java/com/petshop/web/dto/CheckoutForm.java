package com.petshop.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutForm {
    @NotBlank private String customerName;
    @NotBlank @Email private String customerEmail;
    @NotBlank private String customerPhone;
    @NotBlank private String shippingAddress;
    @NotBlank private String city;
    @NotBlank private String state;
    @NotBlank private String postalCode;
    private String notes;
    private String promoCode;
    @NotBlank private String paymentMethod = "SANDBOX_CARD";
    @NotNull private Boolean agreeToTerms = Boolean.FALSE;
}

