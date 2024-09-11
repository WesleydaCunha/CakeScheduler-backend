package com.example.cakescheduler.repositories;

import com.example.cakescheduler.domain.user.UserClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserClientRepository extends JpaRepository<UserClient, String> {
    Optional<UserClient> findByEmail(String email);

    Optional<UserClient> findById(String user);
}
