package com.petshop.shared.dto;

import com.petshop.shared.enums.BookingStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ServiceBookingDto(
        Long id,
        Long userId,
        String serviceSlug,
        String serviceName,
        String petName,
        String petType,
        LocalDate appointmentDate,
        String notes,
        String customerName,
        String customerEmail,
        String customerPhone,
        BookingStatus status,
        LocalDateTime createdAt
) {
}

