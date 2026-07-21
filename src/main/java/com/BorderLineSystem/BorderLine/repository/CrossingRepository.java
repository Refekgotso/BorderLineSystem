package com.borderlines.repository;

import com.borderlines.model.BorderCrossing;
import com.borderlines.model.Immigrant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CrossingRepository extends JpaRepository<BorderCrossing, Long>, JpaSpecificationExecutor<BorderCrossing> {

    List<BorderCrossing> findByImmigrant(Immigrant immigrant);

    List<BorderCrossing> findByImmigrantAndEntryOrExit(Immigrant immigrant, Boolean entryOrExit);

    List<BorderCrossing> findByBorderPost(String borderPost);

    List<BorderCrossing> findByCrossingTimeBetween(LocalDateTime start, LocalDateTime end);

    long countByEntryOrExit(Boolean entryOrExit);
}