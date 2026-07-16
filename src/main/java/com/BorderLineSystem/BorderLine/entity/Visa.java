package com.BorderLineSystem.BorderLine.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "visas")
@Getter
@Setter
public class Visa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
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