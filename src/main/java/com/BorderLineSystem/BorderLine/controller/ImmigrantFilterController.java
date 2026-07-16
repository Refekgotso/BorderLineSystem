package com.BorderLineSystem.BorderLine.controller;

import com.BorderLineSystem.BorderLine.entity.Immigrant;
import com.BorderLineSystem.BorderLine.repository.ImmigrantRepository;
import com.BorderLineSystem.BorderLine.response.Response;
import com.BorderLineSystem.BorderLine.specification.ImmigrantSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/immigrants")
public class ImmigrantFilterController {

    private final ImmigrantRepository immigrantRepository;

    public ImmigrantFilterController(ImmigrantRepository immigrantRepository) {
        this.immigrantRepository = immigrantRepository;
    }

    @GetMapping
    public Response<Page<Immigrant>> getFilteredImmigrants(
            @RequestParam(required = false) String nationality,
            @RequestParam(required = false) String visaType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate entryDateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate entryDateTo,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fullName,asc") String sort
    ) {
        String[] sortParams = sort.split(",");
        Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortParams[0]));

        Specification<Immigrant> spec = Specification
                .where(ImmigrantSpecification.hasNationality(nationality))
                .and(ImmigrantSpecification.hasVisaType(visaType))
                .and(ImmigrantSpecification.entryDateBetween(entryDateFrom, entryDateTo))
                .and(ImmigrantSpecification.search(search));

        Page<Immigrant> result = immigrantRepository.findAll(spec, pageable);
        return Response.success("Immigrants retrieved successfully", result);
    }
}