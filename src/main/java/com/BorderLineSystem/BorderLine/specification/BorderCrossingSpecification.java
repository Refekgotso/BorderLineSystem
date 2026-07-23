package com.BorderLineSystem.BorderLine.specification;

import org.springframework.data.jpa.domain.Specification;

import com.BorderLineSystem.BorderLine.entity.BorderCrossing;

/**
 * Reusable filters for GET /crossings, e.g.:
 *
 * Specification.where(BorderCrossingSpecification.hasImmigrantId(immigrantId))
 *     .and(BorderCrossingSpecification.hasBorderPost(borderPost));
 */
public final class BorderCrossingSpecification {

    private BorderCrossingSpecification() {
        // utility class, no instances
    }

    public static Specification<BorderCrossing> hasImmigrantId(Long immigrantId) {
        return (root, query, cb) -> {
            if (immigrantId == null) {
                return null;
            }
            return cb.equal(root.get("immigrant").get("id"), immigrantId);
        };
    }

    public static Specification<BorderCrossing> hasBorderPost(String borderPost) {
        return (root, query, cb) -> {
            if (borderPost == null || borderPost.isBlank()) {
                return null;
            }
            return cb.equal(cb.lower(root.get("borderPost")), borderPost.toLowerCase());
        };
    }
}
