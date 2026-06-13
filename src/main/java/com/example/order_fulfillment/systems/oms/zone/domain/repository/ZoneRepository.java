package com.example.order_fulfillment.systems.oms.zone.domain.repository;

import com.example.order_fulfillment.systems.oms.zone.domain.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ZoneRepository extends JpaRepository<Zone, String> {
    // 주소에서 가장 세밀하게 일치하는 권역(region 길이 기준 내림차순)을 반환
    @Query("SELECT z FROM Zone z WHERE :address LIKE CONCAT(z.region, '%') ORDER BY LENGTH(z.region) DESC LIMIT 1")
    Optional<Zone> findByAddress(@Param("address") String address);
}
