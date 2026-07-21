package com.BorderLineSystem.BorderLine.repository;

import com.BorderLineSystem.BorderLine.entity.BorderCrossing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BorderCrossingRepository extends JpaRepository<BorderCrossing, Long>,
        JpaSpecificationExecutor<BorderCrossing> {
}