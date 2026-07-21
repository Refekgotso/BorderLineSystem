package com.borderlines.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "visas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VisaType type;

    @Column(nullable = false)
    private Integer durationDays;

    @Column(nullable = false)
    private LocalDate issueDate;

    @Column(nullable = false)
    private LocalDate expiryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "immigrant_id", nullable = false)
    private Immigrant immigrant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VisaStatus status = VisaStatus.ACTIVE;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public enum VisaType {
        BUSINESS,
        VACATION,
        STUDY,
        TRANSIT,
        WORK
    }

    public enum VisaStatus {
        ACTIVE,
        EXPIRED,
        CANCELLED
    }
}