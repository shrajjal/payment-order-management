package com.paymentorder.dto;

import com.paymentorder.entity.Payment;
import com.paymentorder.entity.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PaymentResponse(UUID id, UUID orderId, String transactionId,
                              BigDecimal amount, PaymentStatus status,
                              String paymentMethod, Instant createdAt) {
    public static PaymentResponse from(Payment p) {
        return new PaymentResponse(p.getId(), p.getOrder().getId(), p.getTransactionId(),
                p.getAmount(), p.getStatus(), p.getPaymentMethod(), p.getCreatedAt());
    }
}
