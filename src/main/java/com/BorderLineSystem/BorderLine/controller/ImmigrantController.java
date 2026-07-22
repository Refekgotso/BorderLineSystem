package com.BorderLineSystem.BorderLine.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.BorderLineSystem.BorderLine.dto.ImmigrantDTO;
import com.BorderLineSystem.BorderLine.service.ImmigrantService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/immigrants")
@RequiredArgsConstructor
public class ImmigrantController {

    private final ImmigrantService immigrantService;

    @PostMapping
    public ResponseEntity<ImmigrantDTO> createImmigrant(
            @Valid @RequestBody ImmigrantDTO dto) {

        return new ResponseEntity<>(
                immigrantService.createImmigrant(dto),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ImmigrantDTO>> getAllImmigrants() {

        return ResponseEntity.ok(
                immigrantService.getAllImmigrants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImmigrantDTO> getImmigrantById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                immigrantService.getImmigrantById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImmigrantDTO> updateImmigrant(
            @PathVariable Long id,
            @Valid @RequestBody ImmigrantDTO dto) {

        return ResponseEntity.ok(
                immigrantService.updateImmigrant(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteImmigrant(
            @PathVariable Long id) {

        immigrantService.deleteImmigrant(id);

        return ResponseEntity.ok("Immigrant deleted successfully.");
    }
}