package com.example.cakescheduler.repositories;

import com.example.cakescheduler.domain.orders.Order;
import com.example.cakescheduler.domain.user.UserClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByDeliveryDateBetweenAndOrderStatus(LocalDateTime startOfDay, LocalDateTime endOfDay, String status);

    List<Order> findByOrderStatus(String status);

    List<Order> findByUserClient(UserClient userClient);
}
