package com.example.order_fulfillment.systems.tms.route.domain.repository;

import com.example.order_fulfillment.systems.tms.route.domain.entity.DeliveryRoute;
import com.example.order_fulfillment.systems.tms.route.domain.entity.LoadingCenter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoadingCenterRepository extends JpaRepository<LoadingCenter, Long> {
    List<LoadingCenter> findByDeliveryRouteIn(List<DeliveryRoute> routes);
}
