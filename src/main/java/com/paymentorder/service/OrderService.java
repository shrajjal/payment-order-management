package com.paymentorder.service;

import com.paymentorder.dto.CreateOrderRequest;
import com.paymentorder.dto.OrderResponse;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderResponse create(UUID userId, CreateOrderRequest request);
    List<OrderResponse> findMine(UUID userId);
    OrderResponse findMineById(UUID userId, UUID orderId);
    OrderResponse cancel(UUID userId, UUID orderId);
}
