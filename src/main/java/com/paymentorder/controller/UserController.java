package com.paymentorder.controller;

import com.paymentorder.dto.UserResponse;
import com.paymentorder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/me")
    public UserResponse me(Authentication authentication) {
        return UserResponse.from(userRepository.findByEmail(authentication.getName()).orElseThrow());
    }
}
