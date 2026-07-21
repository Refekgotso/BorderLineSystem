package com.BorderLineSystem.BorderLine.config;

import com.BorderLineSystem.BorderLine.entity.Role;
import com.BorderLineSystem.BorderLine.entity.User;
import com.BorderLineSystem.BorderLine.repository.RoleRepository;
import com.BorderLineSystem.BorderLine.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // 1. Create roles
        Role adminRole = createRoleIfNotExists("ADMIN", "Superuser with full system access");
        Role officerRole = createRoleIfNotExists("OFFICER", "Border control officers with limited write access");
        Role immigrantRole = createRoleIfNotExists("IMMIGRANT", "Public users who self-register");

        logger.info(" All roles seeded successfully!");

        // 2. Create default ADMIN user
        if (userRepository.findByEmail("admin@borderlines.gov").isEmpty()) {
            User adminUser = new User();
            adminUser.setName("System Administrator");
            adminUser.setEmail("admin@borderlines.gov");
            adminUser.setPassword(passwordEncoder.encode("Admin@123"));
            adminUser.setPhoneNumber("+2700000000");
            adminUser.setAddress("Head Office");
            adminUser.setActive(true);
            adminUser.setEmployeeId("ADMIN001");
            adminUser.setDepartment("System Administration");
            adminUser.setRoles(List.of(adminRole));

            userRepository.save(adminUser);
            logger.info(" Default ADMIN user created - Email: admin@borderlines.gov, Password: Admin@123");
            logger.info(" PLEASE CHANGE DEFAULT PASSWORD AFTER FIRST LOGIN!");
        } else {
            logger.info(" ADMIN user already exists");
        }

        logger.info(" Available Roles: ADMIN, OFFICER, IMMIGRANT");
    }

    private Role createRoleIfNotExists(String name, String description) {
        return roleRepository.findByName(name)
                .orElseGet(() -> {
                    Role role = new Role(name, description);
                    Role saved = roleRepository.save(role);
                    logger.info(" {} role created - {}", name, description);
                    return saved;
                });
    }
}