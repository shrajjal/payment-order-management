package com.paymentorder.service;

import com.paymentorder.dto.*;
import com.paymentorder.entity.*;
import com.paymentorder.exception.*;
import com.paymentorder.repository.UserRepository;
import com.paymentorder.security.JwtService;
import com.paymentorder.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {
    @Mock UserRepository repository;
    @Mock JwtService jwtService;
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    AuthServiceImpl service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = new AuthServiceImpl(repository, encoder, jwtService);
        when(jwtService.generateToken(any())).thenReturn("jwt");
        when(jwtService.getExpirationMs()).thenReturn(3600000L);
    }

    @Test
    void registerHashesPassword() {
        when(repository.existsByEmail("user@example.com")).thenReturn(false);
        when(repository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        AuthResponse response = service.register(new RegisterRequest("User", "USER@example.com", "password123"));

        assertEquals("jwt", response.token());
        assertNotEquals("password123", response.user().email()); // email is not password
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(repository).save(captor.capture());
        assertTrue(encoder.matches("password123", captor.getValue().getPassword()));
    }

    @Test
    void duplicateEmailRejected() {
        when(repository.existsByEmail("user@example.com")).thenReturn(true);
        assertThrows(DuplicateEmailException.class,
                () -> service.register(new RegisterRequest("User", "user@example.com", "password123")));
    }

    @Test
    void invalidPasswordRejected() {
        User user = User.builder().email("user@example.com").password(encoder.encode("rightpass")).role(UserRole.USER).build();
        when(repository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        assertThrows(InvalidCredentialsException.class,
                () -> service.login(new LoginRequest("user@example.com", "wrongpass")));
    }
}
