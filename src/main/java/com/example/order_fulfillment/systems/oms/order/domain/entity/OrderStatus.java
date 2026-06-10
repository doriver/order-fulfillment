package com.example.order_fulfillment.systems.oms.order.domain.entity;

public enum OrderStatus {
    PAID,           // 결제완료
    CONFIRMED,      // 주문확인
    IN_WAREHOUSE,   // 입고처리
    DELIVERING,     // 배송중
    DELIVERED,      // 배송완료
    CANCELLED       // 취소
}
