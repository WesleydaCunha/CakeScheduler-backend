package com.example.cakescheduler.repositories;

import com.example.cakescheduler.domain.user.UserEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEmployeeRepository extends JpaRepository<UserEmployee, String> {
    Optional<UserEmployee> findByEmail(String email);

    Optional<UserEmployee> findById(String user);
}
