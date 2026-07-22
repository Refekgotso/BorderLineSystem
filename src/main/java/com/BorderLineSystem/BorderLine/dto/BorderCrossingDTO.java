package com.BorderLineSystem.BorderLine.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorderCrossingDTO {

    private Long id;

    @NotNull(message = "Crossing time is required")
    private LocalDateTime crossingTime;

    private boolean entryOrExit;

    @NotBlank(message = "Border post is required")
    private String borderPost;

    @NotNull(message = "Immigrant ID is required")
    private Long immigrantId;
}