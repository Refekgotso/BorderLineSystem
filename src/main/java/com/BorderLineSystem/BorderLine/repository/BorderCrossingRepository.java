package com.BorderLineSystem.BorderLine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.BorderLineSystem.BorderLine.entity.BorderCrossing;

/**
 * JpaSpecificationExecutor allows dynamic filtering for GET /crossings,
 * e.g. by immigrant id or border post, via BorderCrossingSpecification.
 */
public interface BorderCrossingRepository extends JpaRepository<BorderCrossing, Long>, JpaSpecificationExecutor<BorderCrossing> {

    List<BorderCrossing> findByImmigrant_IdOrderByCrossingTimeAsc(Long immigrantId);
}
