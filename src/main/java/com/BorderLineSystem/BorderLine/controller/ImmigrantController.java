package com.BorderLineSystem.BorderLine.controller;

import com.BorderLineSystem.BorderLine.entity.Notification;
import com.BorderLineSystem.BorderLine.entity.User;
import com.BorderLineSystem.BorderLine.service.AuditService;
import com.BorderLineSystem.BorderLine.service.ImmigrantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/immigrant")
@RequiredArgsConstructor
@PreAuthorize("hasRole('IMMIGRANT')")
public class ImmigrantController {

    private final ImmigrantService immigrantService;
    private final AuditService auditService;

    // Immigrant views their own profile
    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(@AuthenticationPrincipal User currentUser) {
        auditService.logAction(currentUser, "VIEW", "IMMIGRANT", currentUser.getId(), "Viewed own profile");
        return ResponseEntity.ok(currentUser);
    }

    // Immigrant updates their own profile (limited fields)
    @PutMapping("/profile")
    public ResponseEntity<User> updateProfile(@AuthenticationPrincipal User currentUser,
                                              @RequestBody Map<String, String> updates) {
        User updated = immigrantService.updateImmigrantProfile(currentUser, updates);
        auditService.logAction(currentUser, "UPDATE", "IMMIGRANT", currentUser.getId(), "Updated own profile");
        return ResponseEntity.ok(updated);
    }

    // Immigrant views their visa status
    @GetMapping("/visa-status")
    public ResponseEntity<Map<String, Object>> getVisaStatus(@AuthenticationPrincipal User currentUser) {
        Map<String, Object> status = immigrantService.getVisaStatus(currentUser);
        auditService.logAction(currentUser, "VIEW", "VISA", null, "Viewed visa status");
        return ResponseEntity.ok(status);
    }

    // Immigrant views their crossing history
    @GetMapping("/crossings")
    public ResponseEntity<Map<String, Object>> getCrossingHistory(@AuthenticationPrincipal User currentUser) {
        Map<String, Object> history = immigrantService.getCrossingHistory(currentUser);
        auditService.logAction(currentUser, "VIEW", "CROSSING", null, "Viewed crossing history");
        return ResponseEntity.ok(history);
    }

    // Immigrant views their notifications
    @GetMapping("/notifications")
    public ResponseEntity<List<Notification>> getNotifications(@AuthenticationPrincipal User currentUser) {
        List<Notification> notifications = immigrantService.getNotifications(currentUser);
        auditService.logAction(currentUser, "VIEW", "NOTIFICATION", null, "Viewed notifications");
        return ResponseEntity.ok(notifications);
    }

    // Immigrant marks notification as read
    @PutMapping("/notifications/{id}/read")
    public ResponseEntity<Map<String, String>> markNotificationRead(@AuthenticationPrincipal User currentUser,
                                                                    @PathVariable Long id) {
        immigrantService.markNotificationRead(currentUser, id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Notification marked as read");
        return ResponseEntity.ok(response);
    }
}