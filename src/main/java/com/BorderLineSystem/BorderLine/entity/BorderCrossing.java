package com.BorderLineSystem.BorderLine.entity;

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
}
