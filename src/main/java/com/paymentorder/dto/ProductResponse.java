package com.paymentorder.dto;

import com.paymentorder.entity.Product;

import java.math.BigDecimal;
import java.util.UUID;
import java.io.Serializable;

public record ProductResponse(UUID id, String name, String description, BigDecimal price, Integer stock) implements Serializable {
    public static ProductResponse from(Product p) {
        return new ProductResponse(p.getId(), p.getName(), p.getDescription(), p.getPrice(), p.getStock());
    }
}
