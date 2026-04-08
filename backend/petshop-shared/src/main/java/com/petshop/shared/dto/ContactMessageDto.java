package com.petshop.shared.dto;

import com.petshop.shared.enums.RequestStatus;
import java.time.LocalDateTime;

public record ContactMessageDto(
        Long id,
        Long userId,
        String type,
        String name,
        String email,
        String subject,
        String message,
        RequestStatus status,
        LocalDateTime createdAt
) {
}

