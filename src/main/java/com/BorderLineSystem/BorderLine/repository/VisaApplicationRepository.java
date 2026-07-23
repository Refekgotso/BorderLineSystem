package com.BorderLineSystem.BorderLine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BorderLineSystem.BorderLine.entity.VisaApplication;

public interface VisaApplicationRepository extends JpaRepository<VisaApplication, Long> {

    List<VisaApplication> findByStatus(VisaApplication.Status status);

    List<VisaApplication> findByImmigrant_Id(Long immigrantId);
}
