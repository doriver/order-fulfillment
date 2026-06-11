package com.example.order_fulfillment.systems.oms.order.domain.repository;

import com.example.order_fulfillment.systems.oms.order.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
