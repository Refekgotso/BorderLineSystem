package com.BorderLineSystem.BorderLine.dto;

import com.BorderLineSystem.BorderLine.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private Long userId;
    private String email;
    private String name;
    private List<String> roles;
    private String message;
}