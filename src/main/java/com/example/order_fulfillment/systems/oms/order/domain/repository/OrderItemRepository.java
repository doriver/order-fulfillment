package com.example.order_fulfillment.systems.oms.order.domain.repository;

import com.example.order_fulfillment.systems.oms.order.domain.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
