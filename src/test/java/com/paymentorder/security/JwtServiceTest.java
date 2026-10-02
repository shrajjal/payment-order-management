package com.paymentorder.security;

import com.paymentorder.entity.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {
    private final JwtService service = new JwtService(
            "012345678901234567890123456789012345678901234567890123456789",
            3600000L);

    @Test
    void tokenContainsUserEmailAndIsValid() {
        User user = User.builder().email("user@example.com").role(UserRole.USER).build();
        String token = service.generateToken(user);
        assertEquals("user@example.com", service.extractUsername(token));
        assertTrue(service.isTokenValid(token, user));
    }
}
