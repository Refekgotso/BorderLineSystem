package com.BorderLineSystem.BorderLine.specification;

import com.BorderLineSystem.BorderLine.entity.BorderCrossing;
import org.springframework.data.jpa.domain.Specification;

public class BorderCrossingSpecification {

    public static Specification<BorderCrossing> hasImmigrantId(Long immigrantId) {
        return (root, query, cb) ->
                immigrantId == null ? null : cb.equal(root.get("immigrant").get("id"), immigrantId);
    }

    public static Specification<BorderCrossing> hasBorderPost(String borderPost) {
        return (root, query, cb) ->
                borderPost == null ? null : cb.equal(root.get("borderPost"), borderPost);
    }
}