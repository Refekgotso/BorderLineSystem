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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final NotificationService notificationService;
    private final AuditService auditService;

    public AuthService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtils jwtUtils,
                       NotificationService notificationService,
                       AuditService auditService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.notificationService = notificationService;
        this.auditService = auditService;
    }

    // ===== PUBLIC REGISTRATION - IMMIGRANT ONLY =====
    @Transactional
    public void registerImmigrant(RegisterRequest request) {
        logger.info("Registering new immigrant with email: {}", request.getEmail());

        // Validate unique fields
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered");
        }
        if (request.getPassportNumber() != null && userRepository.existsByPassportNumber(request.getPassportNumber())) {
            throw new BadRequestException("Passport number already registered");
        }

        // Get IMMIGRANT role
        Role immigrantRole = roleRepository.findByName("IMMIGRANT")
                .orElseThrow(() -> new ResourceNotFoundException("IMMIGRANT role not found"));

        // Create user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());
        user.setActive(true);

        // Set immigrant-specific fields
        user.setPassportNumber(request.getPassportNumber());
        user.setNationality(request.getNationality());

        // ===== FIX: Correct date parsing =====
        if (request.getDateOfBirth() != null) {
            // If dateOfBirth is already LocalDate
            user.setDateOfBirth(request.getDateOfBirth());
            // If dateOfBirth is String, use: LocalDate.parse(request.getDateOfBirth())
        }

        user.setRoles(List.of(immigrantRole));

        userRepository.save(user);
        logger.info("Immigrant registered successfully: {}", request.getEmail());

        // Send welcome notification
        notificationService.sendWelcomeNotification(user);

        // Log the registration
        auditService.logAction(user, "CREATE", "USER", user.getId(), "Immigrant self-registered");
    }

    // ===== ADMIN ONLY - Create Officer =====
    @Transactional
    public void registerOfficer(RegisterRequest request) {
        logger.info("Creating officer account: {}", request.getEmail());

        validateUniqueFields(request);

        Role officerRole = roleRepository.findByName("OFFICER")
                .orElseThrow(() -> new ResourceNotFoundException("OFFICER role not found"));

        User user = createUser(request, List.of(officerRole));
        user.setEmployeeId(request.getEmployeeId());
        user.setDepartment(request.getDepartment());

        userRepository.save(user);
        logger.info("Officer account created: {}", request.getEmail());
    }

    // ===== ADMIN ONLY - Create Admin =====
    @Transactional
    public void registerAdmin(RegisterRequest request) {
        logger.info("Creating admin account: {}", request.getEmail());

        validateUniqueFields(request);

        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new ResourceNotFoundException("ADMIN role not found"));

        User user = createUser(request, List.of(adminRole));
        user.setEmployeeId(request.getEmployeeId());
        user.setDepartment(request.getDepartment());

        userRepository.save(user);
        logger.info("Admin account created: {}", request.getEmail());
    }

    // ===== PUBLIC LOGIN =====
    public String login(LoginRequest request) {
        logger.info("Login attempt for email: {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid credentials"));

        if (!user.getActive()) {
            throw new BadRequestException("Account is inactive. Please contact support.");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }

        String token = jwtUtils.generateToken(user.getEmail());
        logger.info("Login successful for user: {}", request.getEmail());

        auditService.logAction(user, "LOGIN", "USER", user.getId(), "User logged in");

        return token;
    }

    // ===== HELPER METHODS =====
    private void validateUniqueFields(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered");
        }
        if (request.getEmployeeId() != null && userRepository.existsByEmployeeId(request.getEmployeeId())) {
            throw new BadRequestException("Employee ID already exists");
        }
    }

    private User createUser(RegisterRequest request, List<Role> roles) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());
        user.setActive(true);
        user.setRoles(roles);
        return user;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public List<String> getUserRoles(String email) {
        User user = getUserByEmail(email);
        return user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }
}