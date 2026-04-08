package com.petshop.commerce.service;

import com.petshop.shared.enums.PaymentStatus;

public record PaymentResult(
        String provider,
        String providerReference,
        PaymentStatus status,
        String rawResponse
) {
}

