package com.BorderLineSystem.BorderLine.repository;

import com.BorderLineSystem.BorderLine.entity.AuditLog;
import com.BorderLineSystem.BorderLine.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByUser(User user);

    List<AuditLog> findByAction(String action);

    List<AuditLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

    List<AuditLog> findByResourceAndResourceId(String resource, Long resourceId);
}