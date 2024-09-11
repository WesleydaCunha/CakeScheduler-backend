package com.example.cakescheduler.repositories;

import com.example.cakescheduler.domain.cake.Filling;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FillingRepository extends JpaRepository<Filling, Long> {
}