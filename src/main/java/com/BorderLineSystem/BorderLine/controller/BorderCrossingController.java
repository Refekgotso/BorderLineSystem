package com.BorderLineSystem.BorderLine.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.BorderLineSystem.BorderLine.dto.BorderCrossingDTO;
import com.BorderLineSystem.BorderLine.service.BorderCrossingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/border-crossings")
@RequiredArgsConstructor
public class BorderCrossingController {

    private final BorderCrossingService borderCrossingService;

    @PostMapping
    public ResponseEntity<BorderCrossingDTO> createBorderCrossing(
            @Valid @RequestBody BorderCrossingDTO dto) {

        return new ResponseEntity<>(
                borderCrossingService.createBorderCrossing(dto),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BorderCrossingDTO>> getAllBorderCrossings() {

        return ResponseEntity.ok(borderCrossingService.getAllBorderCrossings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorderCrossingDTO> getBorderCrossingById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                borderCrossingService.getBorderCrossingById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BorderCrossingDTO> updateBorderCrossing(
            @PathVariable Long id,
            @Valid @RequestBody BorderCrossingDTO dto) {

        return ResponseEntity.ok(
                borderCrossingService.updateBorderCrossing(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBorderCrossing(
            @PathVariable Long id) {

        borderCrossingService.deleteBorderCrossing(id);

        return ResponseEntity.ok("Border crossing deleted successfully.");
    }
}