package com.paymentorder.controller;

import com.paymentorder.dto.PaymentResponse;
import com.paymentorder.repository.UserRepository;
import com.paymentorder.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final UserRepository userRepository;

    @PostMapping("/{orderId}")
    public PaymentResponse pay(@PathVariable UUID orderId,
                               @RequestParam(defaultValue = "SIMULATED") String paymentMethod,
                               org.springframework.security.core.Authentication authentication) {
        return paymentService.pay(userId(authentication), orderId, paymentMethod);
    }

    @GetMapping("/{orderId}")
    public PaymentResponse find(@PathVariable UUID orderId,
                                org.springframework.security.core.Authentication authentication) {
        return paymentService.findByOrder(userId(authentication), orderId);
    }

    private UUID userId(org.springframework.security.core.Authentication authentication) {
        return userRepository.findByEmail(authentication.getName()).orElseThrow().getId();
    }
}
