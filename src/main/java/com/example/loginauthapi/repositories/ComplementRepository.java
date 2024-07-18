package com.example.loginauthapi.repositories;

import com.example.loginauthapi.domain.cake.Complement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplementRepository extends JpaRepository<Complement, Long> {
}