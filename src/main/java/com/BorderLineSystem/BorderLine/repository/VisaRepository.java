package com.BorderLineSystem.BorderLine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BorderLineSystem.BorderLine.entity.Visa;

public interface VisaRepository extends JpaRepository<Visa, Long> {

    List<Visa> findByImmigrant_Id(Long immigrantId);

    List<Visa> findByType(Visa.VisaType type);
}
