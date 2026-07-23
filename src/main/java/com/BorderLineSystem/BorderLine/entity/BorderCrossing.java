package com.borderlines.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

package com.BorderLineSystem.BorderLine.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "border_crossings")
@Getter
@Setter
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * A single recorded entry or exit event for an immigrant at a border post.
 */
@Entity
@Table(name = "border_crossings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorderCrossing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime crossingTime;

    @Column(nullable = false)
    private Boolean entryOrExit; // true = ENTRY, false = EXIT

    private LocalDateTime crossingTime;

    private boolean entry; // true = entry, false = exit

    private String borderPost;

    @ManyToOne
    @JoinColumn(name = "immigrant_id")
    private Immigrant immigrant;
}
    @NotNull(message = "Crossing time is required")
    @Column(nullable = false)
    private LocalDateTime crossingTime;

    /** true = entry into South Africa, false = exit from South Africa. */
    @Column(nullable = false)
    private boolean entryOrExit;

    @NotBlank(message = "Border post is required")
    @Column(nullable = false)
    private String borderPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "immigrant_id", nullable = false)
    private Immigrant immigrant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visa_id")
    private Visa visa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recorded_by", nullable = false)
    private User recordedBy;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
}
