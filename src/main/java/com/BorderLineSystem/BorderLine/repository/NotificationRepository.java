package com.BorderLineSystem.BorderLine.repository;

import com.BorderLineSystem.BorderLine.entity.Notification;
import com.BorderLineSystem.BorderLine.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserAndIsReadFalse(User user);

    List<Notification> findByUser(User user);

    List<Notification> findByType(String type);
}