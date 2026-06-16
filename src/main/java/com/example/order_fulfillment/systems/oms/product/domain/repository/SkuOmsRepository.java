package com.example.order_fulfillment.systems.oms.product.domain.repository;

import com.example.order_fulfillment.systems.oms.product.domain.entity.SkuOms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkuOmsRepository extends JpaRepository<SkuOms, String> {
}
