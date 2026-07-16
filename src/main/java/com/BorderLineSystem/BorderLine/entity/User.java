package com.BorderLineSystem.BorderLine.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password; // BCrypt hash

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    private String address;

    @Column(name = "profile_url")
    private String profileUrl;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(name = "employee_id", unique = true)
    private String employeeId; // For officers/admin staff

    @Column(name = "department")
    private String department; // e.g., "Border Control", "Immigration Services"

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    // Helper method to add a role
    public void addRole(Role role) {
        if (this.roles == null) {
            this.roles = new ArrayList<>();
        }
        this.roles.add(role);
    }

    // Helper method to check if user has a specific role
    public boolean hasRole(String roleName) {
        if (this.roles == null || this.roles.isEmpty()) {
            return false;
        }
        return this.roles.stream()
                .anyMatch(role -> role.getName().equals(roleName));
    }

    // Helper method to check if user has any of the given roles
    public boolean hasAnyRole(String... roleNames) {
        if (this.roles == null || this.roles.isEmpty()) {
            return false;
        }
        for (String roleName : roleNames) {
            if (hasRole(roleName)) {
                return true;
            }
        }
        return false;
    }
}