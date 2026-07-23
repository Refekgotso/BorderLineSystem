package com.borderlines.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

package com.BorderLineSystem.BorderLine.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "visas")
@Getter
@Setter
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A single visa issued to an immigrant. An immigrant may hold many visas
 * over time, so this is the "many" side of a OneToMany relationship.
 */
@Entity
@Table(name = "visas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    private VisaType type;

    private Integer durationDays;
    private LocalDate issueDate;
    private LocalDate expiryDate;

    @ManyToOne
    @JoinColumn(name = "immigrant_id")
    private Immigrant immigrant;

    public enum VisaType {
        BUSINESS, VACATION, STUDY
    }
}
    @Column(nullable = false)
    private VisaType type;

    @Positive(message = "Duration must be a positive number of days")
    @Column(nullable = false)
    private int durationDays;

    @NotNull(message = "Issue date is required")
    @Column(nullable = false)
    private LocalDate issueDate;

    @NotNull(message = "Expiry date is required")
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
    public enum VisaType {
        BUSINESS,
        VACATION,
        STUDY
    }
}
