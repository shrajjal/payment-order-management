package com.paymentorder.dto;

import com.paymentorder.entity.Order;
import com.paymentorder.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderResponse(UUID id, BigDecimal totalAmount, OrderStatus status,
                            Instant createdAt, List<OrderItemResponse> items) {
    public static OrderResponse from(Order order) {
        List<OrderItemResponse> items = order.getItems().stream()
                .map(i -> new OrderItemResponse(
                        i.getProduct().getId(),
                        i.getProduct().getName(),
                        i.getQuantity(),
                        i.getUnitPrice(),
                        i.getSubtotal()))
                .toList();
        return new OrderResponse(order.getId(), order.getTotalAmount(), order.getStatus(),
                order.getCreatedAt(), items);
    }
}
