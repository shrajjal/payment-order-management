package com.paymentorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PaymentOrderManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentOrderManagementApplication.class, args);
    }
}
