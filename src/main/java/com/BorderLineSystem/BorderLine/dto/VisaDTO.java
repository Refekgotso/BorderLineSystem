package com.BorderLineSystem.BorderLine.dto;

import java.time.LocalDate;

import com.BorderLineSystem.BorderLine.entity.Visa.VisaType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisaDTO {

    private Long id;

    @NotNull(message = "Visa type is required")
    private VisaType type;

    @Positive(message = "Duration must be greater than zero")
    private int durationDays;

    @NotNull(message = "Issue date is required")
    private LocalDate issueDate;

    @NotNull(message = "Expiry date is required")
    private LocalDate expiryDate;

    @NotNull(message = "Immigrant ID is required")
    private Long immigrantId;
}