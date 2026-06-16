package com.example.order_fulfillment.systems.wms.stock.domain.repository;

import com.example.order_fulfillment.systems.wms.stock.domain.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findBySku_CodeIn(List<String> skuCodes);
}
