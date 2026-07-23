package com.BorderLineSystem.BorderLine.controller;

import com.BorderLineSystem.BorderLine.entity.BorderCrossing;
import com.BorderLineSystem.BorderLine.repository.BorderCrossingRepository;
import com.BorderLineSystem.BorderLine.response.Response;
import com.BorderLineSystem.BorderLine.specification.BorderCrossingSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crossings")
public class BorderCrossingFilterController {

    private final BorderCrossingRepository borderCrossingRepository;

    public BorderCrossingFilterController(BorderCrossingRepository borderCrossingRepository) {
        this.borderCrossingRepository = borderCrossingRepository;
    }

    @GetMapping
    public Response<Page<BorderCrossing>> getFilteredCrossings(
            @RequestParam(required = false) Long immigrantId,
            @RequestParam(required = false) String borderPost,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "crossingTime,desc") String sort
    ) {
        String[] sortParams = sort.split(",");
        Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc")
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortParams[0]));

        Specification<BorderCrossing> spec = Specification
                .where(BorderCrossingSpecification.hasImmigrantId(immigrantId))
                .and(BorderCrossingSpecification.hasBorderPost(borderPost));

        Page<BorderCrossing> result = borderCrossingRepository.findAll(spec, pageable);
        return Response.success("Crossings retrieved successfully", result);
    }
}