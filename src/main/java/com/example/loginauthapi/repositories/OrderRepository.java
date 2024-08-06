package com.example.loginauthapi.repositories;

import com.example.loginauthapi.domain.orders.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, String> {
}
