package com.BorderLineSystem.BorderLine.dto;

import java.time.LocalDate;

import com.BorderLineSystem.BorderLine.entity.Visa.VisaType;
import com.BorderLineSystem.BorderLine.entity.VisaApplication.Status;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisaApplicationDTO {

    private Long id;

    @NotNull(message = "Visa type is required")
    private VisaType requestedVisaType;

    @NotNull(message = "Submission date is required")
    private LocalDate submissionDate;

    private Status status;

    @NotNull(message = "Immigrant ID is required")
    private Long immigrantId;
}