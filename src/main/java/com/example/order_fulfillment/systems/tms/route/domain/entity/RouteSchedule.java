package com.example.order_fulfillment.systems.tms.route.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
    배송 코스별 배송 회차
 */
@Entity
@Table(indexes = {
        @Index(name = "idx_delivery_route", columnList = "delivery_route_code")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RouteSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_route_code", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    @NotNull
    private DeliveryRoute deliveryRoute;
}
