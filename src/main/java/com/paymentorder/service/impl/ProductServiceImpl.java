package com.paymentorder.service.impl;

import com.paymentorder.dto.*;
import com.paymentorder.entity.Product;
import com.paymentorder.exception.ResourceNotFoundException;
import com.paymentorder.repository.ProductRepository;
import com.paymentorder.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    @Override
    @Cacheable("products")
    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return repository.findAll().stream().map(ProductResponse::from).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "product", key = "#id")
    @Transactional(readOnly = true)
    public ProductResponse findById(UUID id) {
        return ProductResponse.from(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id)));
    }

    @Override
    @CacheEvict(value = "products", allEntries = true)
    @Transactional
    public ProductResponse create(ProductRequest request) {
        Product p = Product.builder()
                .name(request.name().trim())
                .description(request.description())
                .price(request.price())
                .stock(request.stock())
                .build();
        return ProductResponse.from(repository.save(p));
    }

    @Override
    @CacheEvict(value = {"products", "product"}, allEntries = true)
    @Transactional
    public ProductResponse update(UUID id, ProductRequest request) {
        Product p = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        p.setName(request.name().trim());
        p.setDescription(request.description());
        p.setPrice(request.price());
        p.setStock(request.stock());
        return ProductResponse.from(repository.save(p));
    }

    @Override
    @CacheEvict(value = {"products", "product"}, allEntries = true)
    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Product not found: " + id);
        repository.deleteById(id);
    }
}
