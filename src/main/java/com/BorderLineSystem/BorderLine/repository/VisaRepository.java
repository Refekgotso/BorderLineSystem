package com.borderlines.repository;

import com.borderlines.model.Immigrant;
import com.borderlines.model.Visa;
import com.borderlines.model.Visa.VisaStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VisaRepository extends JpaRepository<Visa, Long> {

    List<Visa> findByImmigrant(Immigrant immigrant);

    List<Visa> findByStatus(VisaStatus status);

    List<Visa> findByExpiryDateBefore(LocalDate date);

    List<Visa> findByImmigrantAndStatus(Immigrant immigrant, VisaStatus status);
}