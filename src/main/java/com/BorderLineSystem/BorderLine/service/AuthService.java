package com.BorderLineSystem.BorderLine.service;

import com.BorderLineSystem.BorderLine.dto.LoginRequest;
import com.BorderLineSystem.BorderLine.dto.RegisterRequest;
import com.BorderLineSystem.BorderLine.entity.Role;
import com.BorderLineSystem.BorderLine.entity.User;
import com.BorderLineSystem.BorderLine.exception.BadRequestException;
import com.BorderLineSystem.BorderLine.exception.ResourceNotFoundException;
import com.BorderLineSystem.BorderLine.repository.RoleRepository;
import com.BorderLineSystem.BorderLine.repository.UserRepository;
import com.BorderLineSystem.BorderLine.security.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @Transactional
    public void register(RegisterRequest request) {
        logger.info("Registering new user with email: {}", request.getEmail());

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            logger.warn("Registration failed - Email already exists: {}", request.getEmail());
            throw new BadRequestException("Email already registered");
        }

        // Create new user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());
        user.setActive(true);

        // Get OFFICER role (default role for new users)
        Role defaultRole = roleRepository.findByName("OFFICER")
                .orElseThrow(() -> {
                    logger.error("OFFICER role not found in database");
                    return new ResourceNotFoundException("Default role not found");
                });

        user.addRole(defaultRole);

        // Save user
        userRepository.save(user);
        logger.info("User registered successfully: {}", request.getEmail());
    }

    public String login(LoginRequest request) {
        logger.info("Login attempt for email: {}", request.getEmail());

        // Find user by email - same error for wrong email/password
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    logger.warn("Login failed - Invalid credentials: {}", request.getEmail());
                    return new BadRequestException("Invalid credentials");
                });

        // Check if user is active
        if (!user.getActive()) {
            logger.warn("Login failed - Inactive account: {}", request.getEmail());
            throw new BadRequestException("Account is inactive. Please contact support.");
        }

        // Verify password - same error for wrong password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            logger.warn("Login failed - Invalid credentials: {}", request.getEmail());
            throw new BadRequestException("Invalid credentials");
        }

        // Generate JWT token
        String token = jwtUtils.generateToken(user.getEmail());
        logger.info("Login successful for user: {}", request.getEmail());

        return token;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.error("User not found with email: {}", email);
                    return new ResourceNotFoundException("User not found");
                });
    }

    public List<String> getUserRoles(String email) {
        User user = getUserByEmail(email);
        return user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }
}