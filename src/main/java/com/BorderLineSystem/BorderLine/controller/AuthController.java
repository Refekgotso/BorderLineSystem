package com.BorderLineSystem.BorderLine.controller;

import com.BorderLineSystem.BorderLine.dto.AuthResponse;
import com.BorderLineSystem.BorderLine.dto.LoginRequest;
import com.BorderLineSystem.BorderLine.dto.RegisterRequest;
import com.BorderLineSystem.BorderLine.entity.User;
import com.BorderLineSystem.BorderLine.service.AuthService;
import com.BorderLineSystem.BorderLine.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication and Registration APIs")
public class AuthController {

    private final AuthService authService;
    private final AuditService auditService;

    public AuthController(AuthService authService, AuditService auditService) {
        this.authService = authService;
        this.auditService = auditService;
    }

    @Operation(summary = "Register as Immigrant", description = "Public registration for immigrants. Anyone can register.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registration successful"),
            @ApiResponse(responseCode = "400", description = "Invalid input or email already exists")
    })
    @PostMapping("/register/immigrant")
    public ResponseEntity<Map<String, Object>> registerImmigrant(@Valid @RequestBody RegisterRequest request) {
        authService.registerImmigrant(request);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Immigrant registered successfully! Please check your email for verification.");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Login", description = "Login for all users (Immigrant, Officer, Admin)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        String token = authService.login(request);
        User user = authService.getUserByEmail(request.getEmail());

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setUserId(user.getId());
        response.setEmail(user.getEmail());
        response.setName(user.getName());
        response.setRoles(authService.getUserRoles(request.getEmail()));
        response.setMessage("Login successful");

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create Officer", description = "Admin only - Creates a new officer account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Officer created successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied (Admin only)")
    })
    @PostMapping("/register/officer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> registerOfficer(@Valid @RequestBody RegisterRequest request,
                                                               @AuthenticationPrincipal User admin) {
        authService.registerOfficer(request);
        auditService.logAction(admin, "CREATE", "USER", null, "Created officer: " + request.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Officer account created successfully!");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create Admin", description = "Admin only - Creates a new admin account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Admin created successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied (Admin only)")
    })
    @PostMapping("/register/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> registerAdmin(@Valid @RequestBody RegisterRequest request,
                                                             @AuthenticationPrincipal User admin) {
        authService.registerAdmin(request);
        auditService.logAction(admin, "CREATE", "USER", null, "Created admin: " + request.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Admin account created successfully!");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Logout", description = "Logout current user")
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(@AuthenticationPrincipal User user) {
        auditService.logAction(user, "LOGOUT", "USER", user.getId(), "User logged out");
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Logout successful");
        return ResponseEntity.ok(response);
    }
}