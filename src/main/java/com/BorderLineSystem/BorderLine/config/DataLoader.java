package com.BorderLineSystem.BorderLine.config;

import com.BorderLineSystem.BorderLine.entity.Role;
import com.BorderLineSystem.BorderLine.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);
    private final RoleRepository roleRepository;

    public DataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        // Create ADMIN role
        if (roleRepository.findByName("ADMIN").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            roleRepository.save(adminRole);
            logger.info("ADMIN role created - Superuser with full access");
        }

        // Create OFFICER role
        if (roleRepository.findByName("OFFICER").isEmpty()) {
            Role officerRole = new Role();
            officerRole.setName("OFFICER");
            roleRepository.save(officerRole);
            logger.info("OFFICER role created - Border control officers");
        }

        // Create VIEWER role
        if (roleRepository.findByName("VIEWER").isEmpty()) {
            Role viewerRole = new Role();
            viewerRole.setName("VIEWER");
            roleRepository.save(viewerRole);
            logger.info("VIEWER role created - Read-only access");
        }

        logger.info("All roles seeded successfully!");
    }
}