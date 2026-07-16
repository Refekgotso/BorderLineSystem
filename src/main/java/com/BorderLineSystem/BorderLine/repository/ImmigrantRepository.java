package com.BorderLineSystem.BorderLine.repository;

import com.BorderLineSystem.BorderLine.entity.Immigrant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ImmigrantRepository extends JpaRepository<Immigrant, Long>,
        JpaSpecificationExecutor<Immigrant> {
}