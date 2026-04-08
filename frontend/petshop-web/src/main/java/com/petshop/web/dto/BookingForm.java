package com.petshop.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingForm {
    @NotBlank private String serviceSlug;
    @NotBlank private String serviceName;
    @NotBlank private String petName;
    @NotBlank private String petType;
    @NotNull @FutureOrPresent private LocalDate appointmentDate;
    private String notes;
    @NotBlank private String customerName;
    @NotBlank @Email private String customerEmail;
    @NotBlank private String customerPhone;
}

