package com.BorderLineSystem.BorderLine.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "immigrants")
@Getter
@Setter
public class Immigrant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String fullName;

    @NotBlank
    @Pattern(regexp = "^[A-Z0-9]{6,15}$", message = "Invalid passport number format")
    @Column(unique = true)
    private String passportNumber;

    @NotBlank
    private String nationality;

    private LocalDate dateOfBirth;

    private String gender;

    private String uniqueIdFormat;

    @OneToMany(mappedBy = "immigrant", cascade = CascadeType.ALL)
    private List<Visa> visas;

    @OneToMany(mappedBy = "immigrant", cascade = CascadeType.ALL)
    private List<BorderCrossing> crossings;
}