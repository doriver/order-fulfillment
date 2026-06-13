package com.example.order_fulfillment.systems.tms.route.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

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

    @Column(nullable = false)
    private int roundNumber; // 회차 번호

    @Column(nullable = false)
    @NotNull
    private LocalTime departureTime; // 출발 시간

    private LocalTime avgArrivalTime; // 평균 도착 시간

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP",
            insertable = false, updatable = false // DB가 직접 입력하므로 ,JPA는 신경 끄라는 의미
    )
    private LocalDateTime updatedAt;
}
