package com.ecommerce.orderService.repository;

import com.ecommerce.orderService.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
