package com.example.cakescheduler.repositories;

import com.example.cakescheduler.domain.cake.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
}