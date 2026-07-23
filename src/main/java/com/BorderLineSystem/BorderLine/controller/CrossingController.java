package com.borderlines.controller;

import com.borderlines.model.BorderCrossing;
import com.borderlines.model.User;
import com.borderlines.service.CrossingService;
import com.borderlines.util.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crossings")
@RequiredArgsConstructor
public class CrossingController {

    private final CrossingService crossingService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<BorderCrossing>>> getAllCrossings(Pageable pageable) {
        Page<BorderCrossing> crossings = crossingService.getAllCrossings(pageable);
        return ResponseEntity.ok(new ResponseWrapper<>("success", "Crossings retrieved", crossings));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<BorderCrossing>> getCrossingById(@PathVariable Long id) {
        BorderCrossing crossing = crossingService.getCrossingById(id);
        return ResponseEntity.ok(new ResponseWrapper<>("success", "Crossing retrieved", crossing));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<BorderCrossing>> recordCrossing(
            @RequestBody BorderCrossing crossing,
            @RequestParam Long immigrantId,
            @AuthenticationPrincipal User user) {
        BorderCrossing saved = crossingService.recordCrossing(crossing, immigrantId, user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper<>("success", "Crossing recorded successfully", saved));
    }

    @GetMapping("/immigrant/{immigrantId}")
    public ResponseEntity<ResponseWrapper<List<BorderCrossing>>> getCrossingsForImmigrant(
            @PathVariable Long immigrantId) {
        List<BorderCrossing> crossings = crossingService.getCrossingsForImmigrant(immigrantId);
        return ResponseEntity.ok(new ResponseWrapper<>("success", "Crossings retrieved", crossings));
    }
}