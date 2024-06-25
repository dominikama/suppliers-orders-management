package com.stockmanagement.adapters.persistence;

import com.stockmanagement.domain.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findByNumber(String orderNumber);
}
