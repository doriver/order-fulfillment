package com.example.order_fulfillment.systems.tms.route.domain.repository;

import com.example.order_fulfillment.systems.tms.route.domain.entity.DeliveryRoute;
import com.example.order_fulfillment.systems.tms.route.domain.entity.RouteSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteScheduleRepository extends JpaRepository<RouteSchedule, Long> {
    List<RouteSchedule> findByDeliveryRouteIn(List<DeliveryRoute> routes);
}
