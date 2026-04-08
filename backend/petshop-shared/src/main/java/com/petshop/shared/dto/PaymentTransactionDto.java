package com.petshop.shared.dto;

import com.petshop.shared.enums.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentTransactionDto(
        Long id,
        Long orderId,
        String provider,
        String providerReference,
        BigDecimal amount,
        String currency,
        PaymentStatus status,
        LocalDateTime paidAt
) {
}

