package com.example.order_fulfillment.systems.tms.route.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/*
    배송 코스
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryRoute {

    @Id
    @Column(nullable = false, length = 20)
    private String code;

    @Column(nullable = false, length = 100)
    @NotNull
    private String name; // 코스명

    @Column(nullable = false)
    private boolean isActive; // 사용여부

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP",
            insertable = false, updatable = false // DB가 직접 입력하므로 ,JPA는 신경 끄라는 의미
    )
    private LocalDateTime updatedAt;

    public static DeliveryRoute create(String code, String name) {
        DeliveryRoute route = new DeliveryRoute();
        route.code = code;
        route.name = name;
        route.isActive = true;
        return route;
    }
}
