package com.vuongkma.products.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Xử lý InvalidBearerTokenException (Token không hợp lệ)
    @ExceptionHandler(InvalidBearerTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Map<String, String>> handleInvalidBearerTokenException(InvalidBearerTokenException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "invalid_token");
        errorResponse.put("message", "The provided token is invalid or has expired.");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    // Xử lý InsufficientAuthenticationException (Xác thực không đủ)
    @ExceptionHandler(InsufficientAuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Map<String, String>> handleInsufficientAuthenticationException(InsufficientAuthenticationException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "insufficient_authentication");
        errorResponse.put("message", "You do not have the necessary permissions to access this resource.");

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
}
