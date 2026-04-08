package com.petshop.shared.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ServiceBookingRequest(
        Long userId,
        @NotBlank String serviceSlug,
        @NotBlank String serviceName,
        @NotBlank String petName,
        @NotBlank String petType,
        @NotNull @FutureOrPresent LocalDate appointmentDate,
        String notes,
        @NotBlank String customerName,
        @NotBlank @Email String customerEmail,
        @NotBlank String customerPhone
) {
}

