package com.BorderLineSystem.BorderLine.specification;

import com.BorderLineSystem.BorderLine.entity.Immigrant;
import com.BorderLineSystem.BorderLine.entity.Visa;
import com.BorderLineSystem.BorderLine.entity.BorderCrossing;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;

public class ImmigrantSpecification {

    public static Specification<Immigrant> hasNationality(String nationality) {
        return (root, query, cb) ->
                nationality == null ? null : cb.equal(root.get("nationality"), nationality);
    }

    public static Specification<Immigrant> hasVisaType(String visaType) {
        return (root, query, cb) -> {
            if (visaType == null) return null;
            Join<Immigrant, Visa> visaJoin = root.join("visas", JoinType.LEFT);
            return cb.equal(visaJoin.get("type"), Visa.VisaType.valueOf(visaType.toUpperCase()));
        };
    }

    public static Specification<Immigrant> entryDateBetween(LocalDate from, LocalDate to) {
        return (root, query, cb) -> {
            if (from == null && to == null) return null;
            Join<Immigrant, BorderCrossing> crossingJoin = root.join("crossings", JoinType.LEFT);
            if (from != null && to != null) {
                return cb.between(crossingJoin.get("crossingTime").as(LocalDate.class), from, to);
            } else if (from != null) {
                return cb.greaterThanOrEqualTo(crossingJoin.get("crossingTime").as(LocalDate.class), from);
            } else {
                return cb.lessThanOrEqualTo(crossingJoin.get("crossingTime").as(LocalDate.class), to);
            }
        };
    }

    public static Specification<Immigrant> search(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) return null;
            String pattern = "%" + keyword.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("fullName")), pattern),
                    cb.like(cb.lower(root.get("passportNumber")), pattern)
            );
        };
    }
}