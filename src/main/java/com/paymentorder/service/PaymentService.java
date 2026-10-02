package com.paymentorder.service;

import com.paymentorder.dto.PaymentResponse;

import java.util.UUID;

public interface PaymentService {
    PaymentResponse pay(UUID userId, UUID orderId, String paymentMethod);
    PaymentResponse findByOrder(UUID userId, UUID orderId);
}
