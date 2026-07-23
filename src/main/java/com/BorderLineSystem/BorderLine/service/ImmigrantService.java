package com.BorderLineSystem.BorderLine.service;

import com.BorderLineSystem.BorderLine.entity.Notification;
import com.BorderLineSystem.BorderLine.entity.User;
import com.BorderLineSystem.BorderLine.exception.BadRequestException;
import com.BorderLineSystem.BorderLine.repository.NotificationRepository;
import com.BorderLineSystem.BorderLine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImmigrantService {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;

    @Transactional
    public User updateImmigrantProfile(User currentUser, Map<String, String> updates) {
        // Only allow updating non-critical fields
        if (updates.containsKey("phoneNumber")) {
            currentUser.setPhoneNumber(updates.get("phoneNumber"));
        }
        if (updates.containsKey("address")) {
            currentUser.setAddress(updates.get("address"));
        }
        if (updates.containsKey("email")) {
            // Validate email is unique
            if (userRepository.existsByEmailAndIdNot(updates.get("email"), currentUser.getId())) {
                throw new BadRequestException("Email already in use");
            }
            currentUser.setEmail(updates.get("email"));
        }

        return userRepository.save(currentUser);
    }

    public List<Notification> getNotifications(User immigrant) {
        return notificationRepository.findByUserAndIsReadFalse(immigrant);
    }

    @Transactional
    public void markNotificationRead(User immigrant, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new BadRequestException("Notification not found"));

        if (!notification.getUser().getId().equals(immigrant.getId())) {
            throw new BadRequestException("Unauthorized to access this notification");
        }

        notification.setIsRead(true);
        notification.setReadAt(java.time.LocalDateTime.now());
        notificationRepository.save(notification);
    }

    public Map<String, Object> getVisaStatus(User immigrant) {
        // Placeholder - will be implemented when visa module is ready
        return Map.of(
                "status", "NO_ACTIVE_VISA",
                "message", "No active visa found. Please apply for a visa."
        );
    }

    public Map<String, Object> getCrossingHistory(User immigrant) {
        // Placeholder - will be implemented when crossing module is ready
        return Map.of(
                "totalCrossings", 0,
                "crossings", List.of(),
                "message", "No crossing history found."
        );
    }
}