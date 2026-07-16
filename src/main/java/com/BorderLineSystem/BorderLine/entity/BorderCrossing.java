package com.BorderLineSystem.BorderLine.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "border_crossings")
@Getter
@Setter
public class BorderCrossing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime crossingTime;

    private boolean entry; // true = entry, false = exit

    private String borderPost;

    @ManyToOne
    @JoinColumn(name = "immigrant_id")
    private Immigrant immigrant;
}