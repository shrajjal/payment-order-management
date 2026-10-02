package com.paymentorder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentorder.dto.*;
import com.paymentorder.service.AuthService;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerTest {
    private MockMvc mvc;
    private AuthService service;

    @BeforeEach
    void setup() {
        service = mock(AuthService.class);
        mvc = MockMvcBuilders.standaloneSetup(new AuthController(service)).build();
    }

    @Test
    void registerReturns201() throws Exception {
        when(service.register(any())).thenReturn(
                new AuthResponse("jwt", "Bearer", 3600, null));

        mvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(
                                new RegisterRequest("Test", "test@example.com", "password123"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value("jwt"));
    }

    @Test
    void loginReturns200() throws Exception {
        when(service.login(any())).thenReturn(
                new AuthResponse("jwt", "Bearer", 3600, null));

        mvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(
                                new LoginRequest("test@example.com", "password123"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt"));
    }
}
