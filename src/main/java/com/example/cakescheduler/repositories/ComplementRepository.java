package com.example.cakescheduler.repositories;

import com.example.cakescheduler.domain.cake.Complement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplementRepository extends JpaRepository<Complement, Long> {
}