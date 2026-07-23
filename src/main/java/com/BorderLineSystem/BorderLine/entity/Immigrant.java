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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A person tracked by the system as they move across South African borders.
 */
@Entity
@Table(name = "immigrants")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @NotBlank(message = "Full name is required")
    @Column(nullable = false)
    private String fullName;

    @NotBlank(message = "Passport number is required")
    @Pattern(regexp = "^[A-Z0-9]{6,12}$", message = "Passport number must be 6-12 uppercase letters/digits")
    @Column(nullable = false, unique = true)
    private String passportNumber;

    @NotBlank(message = "Nationality is required")
    @Column(nullable = false)
    private String nationality;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    /** Whether uniqueId refers to a South African ID number or a foreign passport. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IdentificationType idType;

    @NotBlank(message = "Identification number is required")
    @Column(nullable = false)
    private String idNumber;

    @OneToMany(mappedBy = "immigrant", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Visa> visas = new ArrayList<>();

    @OneToMany(mappedBy = "immigrant", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<BorderCrossing> borderCrossings = new ArrayList<>();

    @OneToMany(mappedBy = "immigrant", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<VisaApplication> applications = new ArrayList<>();

    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }

    public enum IdentificationType {
        SA_ID,
        FOREIGN_PASSPORT
    }
}
