package com.paymentorder.config;

import com.paymentorder.entity.User;
import com.paymentorder.entity.UserRole;
import com.paymentorder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    @Bean
    CommandLineRunner seedAdmin(UserRepository repository, PasswordEncoder encoder) {
        return args -> {
            if (!repository.existsByEmail("admin@example.com")) {
                repository.save(User.builder()
                        .name("Admin")
                        .email("admin@example.com")
                        .password(encoder.encode("Admin@12345"))
                        .role(UserRole.ADMIN)
                        .build());
            }
        };
    }
}
