package com.example.order_fulfillment.systems.oms.zone.domain.repository;

import com.example.order_fulfillment.systems.oms.zone.domain.entity.DeliveryZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DeliveryZoneRepository extends JpaRepository<DeliveryZone, String> {
    // 주소에서 가장 세밀하게 일치하는 권역(region 길이 기준 내림차순)을 반환
    @Query("SELECT z FROM DeliveryZone z WHERE :address LIKE CONCAT(z.region, '%') ORDER BY LENGTH(z.region) DESC LIMIT 1")
    Optional<DeliveryZone> findByAddress(@Param("address") String address);
}
