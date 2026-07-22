package com.BorderLineSystem.BorderLine.specification;

import com.BorderLineSystem.BorderLine.entity.BorderCrossing;
import com.BorderLineSystem.BorderLine.entity.Immigrant;
import com.BorderLineSystem.BorderLine.entity.Visa;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Reusable, composable filters for GET /immigrants. Each method returns a
 * Specification (or null if the filter doesn't apply) so they can be
 * combined in a service layer like:
 *
 * Specification.where(ImmigrantSpecification.hasNationality(nationality))
 *     .and(ImmigrantSpecification.hasVisaType(visaType))
 *     .and(ImmigrantSpecification.hasCrossingBetween(from, to))
 *     .and(ImmigrantSpecification.keywordSearch(search));
 */
public final class ImmigrantSpecification {

    private ImmigrantSpecification() {
        // utility class, no instances
    }

    public static Specification<Immigrant> hasNationality(String nationality) {
        return (root, query, cb) -> {
            if (nationality == null || nationality.isBlank()) {
                return null;
            }
            return cb.equal(cb.lower(root.get("nationality")), nationality.toLowerCase());
        };
    }

    public static Specification<Immigrant> hasVisaType(Visa.VisaType visaType) {
        return (root, query, cb) -> {
            if (visaType == null) {
                return null;
            }
            query.distinct(true);
            Join<Immigrant, Visa> visaJoin = root.join("visas");
            return cb.equal(visaJoin.get("type"), visaType);
        };
    }

    public static Specification<Immigrant> hasCrossingBetween(LocalDate entryDateFrom, LocalDate entryDateTo) {
        return (root, query, cb) -> {
            if (entryDateFrom == null && entryDateTo == null) {
                return null;
            }
            query.distinct(true);
            Join<Immigrant, BorderCrossing> crossingJoin = root.join("borderCrossings");
            List<Predicate> predicates = new ArrayList<>();
            if (entryDateFrom != null) {
                predicates.add(cb.greaterThanOrEqualTo(crossingJoin.get("crossingTime"), entryDateFrom.atStartOfDay()));
            }
            if (entryDateTo != null) {
                predicates.add(cb.lessThanOrEqualTo(crossingJoin.get("crossingTime"), entryDateTo.atTime(LocalTime.MAX)));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Immigrant> keywordSearch(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) {
                return null;
            }
            String likePattern = "%" + keyword.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("passportNumber")), likePattern),
                    cb.like(cb.lower(root.get("fullName")), likePattern)
            );
        };
    }
}
