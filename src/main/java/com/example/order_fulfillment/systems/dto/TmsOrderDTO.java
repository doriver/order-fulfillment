package com.example.order_fulfillment.systems.dto;

import com.example.order_fulfillment.systems.oms.order.domain.entity.Order;

/*
    oms에서 출고, 배송의 결정을 위해 tms에게 전달하는 order관련 데이터
 */
public record TmsOrderDTO(
        String deliveryZoneCode
) {
    public static TmsOrderDTO from(Order order) {
        return new TmsOrderDTO(order.getZone().getCode());
    }
}
