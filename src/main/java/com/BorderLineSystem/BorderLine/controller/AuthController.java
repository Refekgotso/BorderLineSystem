package com.BorderLineSystem.BorderLine.controller;

import com.BorderLineSystem.BorderLine.dto.AuthResponse;
import com.BorderLineSystem.BorderLine.dto.LoginRequest;
import com.BorderLineSystem.BorderLine.dto.RegisterRequest;
import com.BorderLineSystem.BorderLine.entity.User;
import com.BorderLineSystem.BorderLine.exception.BadRequestException;
import com.BorderLineSystem.BorderLine.service.AuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody RegisterRequest request) {
        logger.info("Received registration request for email: {}", request.getEmail());

        authService.register(request);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "User registered successfully");

        logger.info("Registration successful for: {}", request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        logger.info("Received login request for email: {}", request.getEmail());

        String token = authService.login(request);
        User user = authService.getUserByEmail(request.getEmail());

        AuthResponse response = new AuthResponse(
                token,
                user.getEmail(),
                user.getName(),
                authService.getUserRoles(user.getEmail())
        );

        logger.info("Login successful for: {}", request.getEmail());
        return ResponseEntity.ok(response);
    }
}