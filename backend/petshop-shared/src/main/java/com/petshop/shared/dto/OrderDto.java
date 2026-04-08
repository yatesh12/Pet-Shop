package com.petshop.shared.dto;

import com.petshop.shared.enums.OrderStatus;
import com.petshop.shared.enums.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
        Long id,
        String ownerReference,
        Long userId,
        String customerName,
        String customerEmail,
        String customerPhone,
        String shippingAddress,
        String city,
        String state,
        String postalCode,
        OrderStatus status,
        PaymentStatus paymentStatus,
        String paymentReference,
        BigDecimal subtotal,
        BigDecimal discountAmount,
        BigDecimal totalAmount,
        String promoCode,
        LocalDateTime createdAt,
        List<OrderItemDto> items
) {
}

