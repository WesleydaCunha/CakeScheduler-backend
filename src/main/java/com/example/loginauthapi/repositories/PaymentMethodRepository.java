package com.example.loginauthapi.repositories;

import com.example.loginauthapi.domain.cake.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
}