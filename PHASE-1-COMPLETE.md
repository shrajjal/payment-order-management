# Phase 1 — User Management & JWT Authentication

This project contains the complete Phase 1 implementation for the Payment & Order Management System.

## Implemented

- User entity with UUID ID, name, unique email, BCrypt password, and USER/ADMIN role
- UserRepository with email lookup
- RegisterRequest, LoginRequest, AuthResponse, and UserResponse DTOs
- AuthService and AuthServiceImpl
- DuplicateEmailException and InvalidCredentialsException
- JWT generation, extraction, and validation
- JWT authentication filter
- Database-backed CustomUserDetailsService
- Stateless Spring Security configuration
- Public endpoints:
  - POST `/api/auth/register`
  - POST `/api/auth/login`
  - GET `/api/health`
- Protected endpoint:
  - GET `/api/users/me`
- Global exception handling for authentication and validation errors
- Unit tests for AuthService, JwtService, AuthController, and UserController
- Controller tests use Mockito + standalone MockMvc and do not require PostgreSQL

## Manual verification completed

1. Registration returns `201 Created` and a JWT.
2. Login returns `200 OK` and a JWT.
3. `/api/users/me` with a valid Bearer token returns the authenticated user.
4. `/api/users/me` without a token is rejected by Spring Security.

## Run

```bat
mvn clean test
mvn spring-boot:run
```

The application expects PostgreSQL and Redis to be available according to `application.yml` / environment variables.
