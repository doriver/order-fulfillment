package com.example.order_fulfillment.systems.wms.warehouse.domain.repository;

import com.example.order_fulfillment.systems.wms.warehouse.domain.entity.LogisticsCenter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogisticsCenterRepository extends JpaRepository<LogisticsCenter, String> {
}
