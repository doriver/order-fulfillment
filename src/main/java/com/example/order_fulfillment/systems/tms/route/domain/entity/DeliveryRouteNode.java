package com.example.order_fulfillment.systems.tms.route.domain.entity;

import com.example.order_fulfillment.systems.oms.zone.domain.entity.Zone;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/*
    배송코스별 경유지
 */
@Entity
@Table(indexes = {
    @Index(name = "idx_delivery_route", columnList = "delivery_route_code")
    , @Index(name = "idx_zone", columnList = "delivery_zone_code")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryRouteNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_route_code", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    @NotNull
    private DeliveryRoute deliveryRoute;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_zone_code", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    @NotNull
    private Zone zone;

    @Column(nullable = false)
    private int sequence; // 경유 순서

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP",
            insertable = false, updatable = false // DB가 직접 입력하므로 ,JPA는 신경 끄라는 의미
    )
    private LocalDateTime updatedAt;
}
