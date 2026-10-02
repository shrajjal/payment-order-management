package com.paymentorder.controller;

import com.paymentorder.entity.*;
import com.paymentorder.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {
    private MockMvc mvc;
    private UserRepository repository;

    @BeforeEach
    void setup() {
        repository = mock(UserRepository.class);
        mvc = MockMvcBuilders.standaloneSetup(new UserController(repository)).build();
    }

    @Test
    void meReturnsUser() throws Exception {
        User user = User.builder().id(UUID.randomUUID()).name("Test")
                .email("test@example.com").role(UserRole.USER).password("hash").build();
        when(repository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        mvc.perform(get("/api/users/me")
                        .principal(new TestingAuthenticationToken("test@example.com", null)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.role").value("USER"));
    }
}
