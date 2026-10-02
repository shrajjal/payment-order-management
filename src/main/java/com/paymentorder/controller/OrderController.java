package com.paymentorder.controller;

import com.paymentorder.dto.*;
import com.paymentorder.repository.UserRepository;
import com.paymentorder.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserRepository userRepository;

    @PostMapping
    public OrderResponse create(@Valid @RequestBody CreateOrderRequest request,
                                 org.springframework.security.core.Authentication authentication) {
        UUID userId = userId(authentication);
        return orderService.create(userId, request);
    }

    @GetMapping
    public List<OrderResponse> findMine(org.springframework.security.core.Authentication authentication) {
        return orderService.findMine(userId(authentication));
    }

    @GetMapping("/{id}")
    public OrderResponse findById(@PathVariable UUID id,
                                  org.springframework.security.core.Authentication authentication) {
        return orderService.findMineById(userId(authentication), id);
    }

    @PutMapping("/{id}/cancel")
    public OrderResponse cancel(@PathVariable UUID id,
                                org.springframework.security.core.Authentication authentication) {
        return orderService.cancel(userId(authentication), id);
    }

    private UUID userId(org.springframework.security.core.Authentication authentication) {
        return userRepository.findByEmail(authentication.getName()).orElseThrow().getId();
    }
}
