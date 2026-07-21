package com.borderlines.repository;

import com.borderlines.model.Application;
import com.borderlines.model.Application.ApplicationStatus;
import com.borderlines.model.Immigrant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByImmigrant(Immigrant immigrant);

    List<Application> findByStatus(ApplicationStatus status);

    List<Application> findByImmigrantAndStatus(Immigrant immigrant, ApplicationStatus status);
}