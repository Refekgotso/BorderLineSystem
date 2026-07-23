package com.BorderLineSystem.BorderLine.dto;

import java.time.LocalDate;

import com.BorderLineSystem.BorderLine.entity.Immigrant.Gender;
import com.BorderLineSystem.BorderLine.entity.Immigrant.IdentificationType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImmigrantDTO {

    private Long id;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Passport number is required")
    @Pattern(regexp = "^[A-Z0-9]{6,12}$")
    private String passportNumber;

    @NotBlank(message = "Nationality is required")
    private String nationality;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotNull
    private Gender gender;

    @NotNull
    private IdentificationType idType;

    @NotBlank(message = "Identification number is required")
    private String idNumber;
}