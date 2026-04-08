package com.petshop.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressForm {
    private Long id;
    @NotBlank private String label;
    @NotBlank private String lineOne;
    private String lineTwo;
    @NotBlank private String city;
    @NotBlank private String state;
    @NotBlank private String postalCode;
    @NotBlank private String country;
    private boolean defaultAddress;
}

