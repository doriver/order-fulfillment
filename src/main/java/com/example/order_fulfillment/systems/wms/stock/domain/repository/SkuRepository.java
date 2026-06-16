package com.example.order_fulfillment.systems.wms.stock.domain.repository;

import com.example.order_fulfillment.systems.wms.stock.domain.entity.Sku;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkuRepository extends JpaRepository<Sku, String> {
}
