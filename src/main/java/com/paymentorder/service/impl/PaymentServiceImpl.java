package com.paymentorder.service.impl;

import com.paymentorder.dto.PaymentResponse;
import com.paymentorder.entity.*;
import com.paymentorder.exception.*;
import com.paymentorder.repository.*;
import com.paymentorder.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public PaymentResponse pay(UUID userId, UUID orderId, String paymentMethod) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (!order.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Order not found");
        }
        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new OrderStateException("Cannot pay for a cancelled order");
        }
        if (paymentRepository.findByOrderId(orderId).isPresent()) {
            throw new OrderStateException("Payment already exists for this order");
        }

        Payment payment = Payment.builder()
                .order(order)
                .transactionId("TXN-" + UUID.randomUUID())
                .amount(order.getTotalAmount())
                .status(PaymentStatus.SUCCESS)
                .paymentMethod(paymentMethod == null || paymentMethod.isBlank() ? "SIMULATED" : paymentMethod)
                .build();

        order.setStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);
        return PaymentResponse.from(paymentRepository.save(payment));
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse findByOrder(UUID userId, UUID orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
        if (!payment.getOrder().getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Payment not found");
        }
        return PaymentResponse.from(payment);
    }
}
