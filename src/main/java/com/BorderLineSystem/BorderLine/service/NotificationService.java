package com.BorderLineSystem.BorderLine.service;

import com.BorderLineSystem.BorderLine.entity.Notification;
import com.BorderLineSystem.BorderLine.entity.User;
import com.BorderLineSystem.BorderLine.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void sendWelcomeNotification(User user) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setSubject("Welcome to BorderLines");
        notification.setMessage("Welcome to BorderLines! You can now track your visa applications and crossing history.");
        notification.setType("WELCOME");
        notification.setIsRead(false);
        notificationRepository.save(notification);
    }

    public void sendVisaExpiryNotification(User user, String visaType, int daysRemaining) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setSubject("Visa Expiring Soon");
        notification.setMessage("Your " + visaType + " visa expires in " + daysRemaining + " days. Please renew or exit South Africa.");
        notification.setType("VISA_EXPIRY");
        notificationRepository.save(notification);
    }

    public void sendOverstayAlert(User user, long daysOverstayed) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setSubject("Overstay Alert");
        notification.setMessage("You have overstayed by " + daysOverstayed + " days. Please contact Home Affairs immediately.");
        notification.setType("OVERSTAY");
        notificationRepository.save(notification);
    }

    public void notifyOfficersAboutNewApplication(User immigrant, String applicationId) {
        // This will be implemented when the officer notification system is ready
        List<User> officers = List.of(); // Will be fetched from repository
        for (User officer : officers) {
            Notification notification = new Notification();
            notification.setUser(officer);
            notification.setSubject("New Visa Application");
            notification.setMessage("A new visa application from " + immigrant.getName() + " requires review.");
            notification.setType("APPLICATION_STATUS");
            notificationRepository.save(notification);
        }
    }
}