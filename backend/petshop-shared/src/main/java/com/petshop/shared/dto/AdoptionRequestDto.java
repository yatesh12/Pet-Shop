package com.petshop.shared.dto;

import com.petshop.shared.enums.RequestStatus;
import java.time.LocalDateTime;

public record AdoptionRequestDto(
        Long id,
        Long userId,
        String petSlug,
        String petName,
        String customerName,
        String customerEmail,
        String customerPhone,
        String homeType,
        String experienceLevel,
        String notes,
        RequestStatus status,
        LocalDateTime createdAt
) {
}

