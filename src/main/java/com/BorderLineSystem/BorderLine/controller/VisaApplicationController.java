package com.BorderLineSystem.BorderLine.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.BorderLineSystem.BorderLine.dto.VisaApplicationDTO;
import com.BorderLineSystem.BorderLine.service.VisaApplicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/visa-applications")
@RequiredArgsConstructor
public class VisaApplicationController {

    private final VisaApplicationService applicationService;

    @PostMapping
    public ResponseEntity<VisaApplicationDTO> createApplication(
            @Valid @RequestBody VisaApplicationDTO dto) {

        return new ResponseEntity<>(
                applicationService.createApplication(dto),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VisaApplicationDTO>> getAllApplications() {

        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisaApplicationDTO> getApplicationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VisaApplicationDTO> updateApplication(
            @PathVariable Long id,
            @Valid @RequestBody VisaApplicationDTO dto) {

        return ResponseEntity.ok(
                applicationService.updateApplication(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApplication(
            @PathVariable Long id) {

        applicationService.deleteApplication(id);

        return ResponseEntity.ok("Visa application deleted successfully.");
    }
}