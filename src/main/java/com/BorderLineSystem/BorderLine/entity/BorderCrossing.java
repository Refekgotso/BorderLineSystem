package com.borderlines.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "border_crossings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorderCrossing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime crossingTime;

    @Column(nullable = false)
    private Boolean entryOrExit; // true = ENTRY, false = EXIT

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