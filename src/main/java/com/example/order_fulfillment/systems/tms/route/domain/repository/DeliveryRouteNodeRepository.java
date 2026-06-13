package com.example.order_fulfillment.systems.tms.route.domain.repository;

import com.example.order_fulfillment.systems.tms.route.domain.entity.DeliveryRoute;
import com.example.order_fulfillment.systems.tms.route.domain.entity.DeliveryRouteNode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRouteNodeRepository extends JpaRepository<DeliveryRouteNode, Long> {
    List<DeliveryRouteNode> findByZone_Code(String zoneCode);
}
