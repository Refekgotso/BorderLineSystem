package com.BorderLineSystem.BorderLine.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.BorderLineSystem.BorderLine.entity.Immigrant;

/**
 * JpaSpecificationExecutor lets this repository accept dynamic
 * {@link org.springframework.data.jpa.domain.Specification} filters, e.g. the
 * ones built in ImmigrantSpecification, for GET /immigrants filtering.
 */
public interface ImmigrantRepository extends JpaRepository<Immigrant, Long>, JpaSpecificationExecutor<Immigrant> {

    Optional<Immigrant> findByPassportNumber(String passportNumber);

    boolean existsByPassportNumber(String passportNumber);
}
