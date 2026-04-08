package com.petshop.commerce.service;

import java.math.BigDecimal;

public interface PaymentProvider {
    PaymentResult charge(BigDecimal amount, String paymentMethod, String orderReference);
}

