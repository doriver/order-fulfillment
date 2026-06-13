package com.example.order_fulfillment.systems.tms.route.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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


}
