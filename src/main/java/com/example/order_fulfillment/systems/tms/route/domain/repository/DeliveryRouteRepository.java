package com.example.order_fulfillment.systems.tms.route.domain.repository;

import com.example.order_fulfillment.systems.tms.route.domain.entity.DeliveryRoute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRouteRepository extends JpaRepository<DeliveryRoute, String> {
}
