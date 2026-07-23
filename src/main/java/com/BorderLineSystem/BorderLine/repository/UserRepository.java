package com.BorderLineSystem.BorderLine.repository;

import com.BorderLineSystem.BorderLine.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByPassportNumber(String passportNumber);
    boolean existsByPassportNumber(String passportNumber);
    boolean existsByEmailAndIdNot(String email, Long id);
    boolean existsByEmployeeId(String employeeId);
}