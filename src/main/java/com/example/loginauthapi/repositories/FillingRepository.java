package com.example.loginauthapi.repositories;

import com.example.loginauthapi.domain.cake.Filling;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FillingRepository extends JpaRepository<Filling, Long> {
}