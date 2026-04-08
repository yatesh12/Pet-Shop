package com.petshop.shared.dto;

import com.petshop.shared.enums.RequestStatus;
import java.time.LocalDateTime;

public record InquiryDto(
        Long id,
        Long userId,
        String itemSlug,
        String itemType,
        String customerName,
        String customerEmail,
        String phone,
        String message,
        RequestStatus status,
        LocalDateTime createdAt
) {
}

