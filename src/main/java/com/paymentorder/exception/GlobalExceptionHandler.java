package com.paymentorder.exception;

import com.paymentorder.dto.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiErrorResponse> validation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(java.util.stream.Collectors.toMap(
                        e -> e.getField(), e -> e.getDefaultMessage() == null ? "Invalid value" : e.getDefaultMessage(),
                        (a, b) -> a));
        return response(HttpStatus.BAD_REQUEST, "Validation failed", req, errors);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    ResponseEntity<ApiErrorResponse> duplicate(DuplicateEmailException ex, HttpServletRequest req) {
        return response(HttpStatus.CONFLICT, ex.getMessage(), req, null);
    }

    @ExceptionHandler({InvalidCredentialsException.class})
    ResponseEntity<ApiErrorResponse> unauthorized(RuntimeException ex, HttpServletRequest req) {
        return response(HttpStatus.UNAUTHORIZED, ex.getMessage(), req, null);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    ResponseEntity<ApiErrorResponse> notFound(RuntimeException ex, HttpServletRequest req) {
        return response(HttpStatus.NOT_FOUND, ex.getMessage(), req, null);
    }

    @ExceptionHandler({InsufficientStockException.class, OrderStateException.class})
    ResponseEntity<ApiErrorResponse> conflict(RuntimeException ex, HttpServletRequest req) {
        return response(HttpStatus.CONFLICT, ex.getMessage(), req, null);
    }

    private ResponseEntity<ApiErrorResponse> response(HttpStatus status, String message,
                                                       HttpServletRequest req,
                                                       Map<String, String> validation) {
        return ResponseEntity.status(status).body(new ApiErrorResponse(
                Instant.now(), status.value(), status.getReasonPhrase(), message, req.getRequestURI(), validation));
    }
}
