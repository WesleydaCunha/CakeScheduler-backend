package com.example.loginauthapi.repositories;

import com.example.loginauthapi.domain.cake.CakeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CakeModelRepository extends JpaRepository<CakeModel, Long> {
}
