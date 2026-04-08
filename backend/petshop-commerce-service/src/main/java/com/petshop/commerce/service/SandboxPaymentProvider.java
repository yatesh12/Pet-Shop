package com.petshop.commerce.service;

import com.petshop.shared.enums.PaymentStatus;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class SandboxPaymentProvider implements PaymentProvider {

    @Override
    public PaymentResult charge(BigDecimal amount, String paymentMethod, String orderReference) {
        String reference = "sandbox-" + UUID.randomUUID().toString().substring(0, 12);
        String payload = "{\"amount\":\"" + amount + "\",\"paymentMethod\":\"" + paymentMethod + "\",\"orderReference\":\"" + orderReference + "\",\"approved\":true}";
        return new PaymentResult("SANDBOX", reference, PaymentStatus.PAID, payload);
    }
}

