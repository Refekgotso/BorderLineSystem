package com.BorderLineSystem.BorderLine.service;

import com.BorderLineSystem.BorderLine.entity.AuditLog;
import com.BorderLineSystem.BorderLine.entity.User;
import com.BorderLineSystem.BorderLine.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public void logAction(User user, String action, String resource, Long resourceId, String details) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes())
                .getRequest();

        AuditLog auditLog = new AuditLog();
        auditLog.setUser(user);
        auditLog.setAction(action);
        auditLog.setResource(resource);
        auditLog.setResourceId(resourceId);
        auditLog.setDetails(details);
        auditLog.setIpAddress(getClientIp(request));
        auditLog.setUserAgent(request.getHeader("User-Agent"));
        auditLog.setTimestamp(LocalDateTime.now());

        auditLogRepository.save(auditLog);
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}