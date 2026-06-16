package com.example.order_fulfillment.systems.integration.dto;

import java.util.List;

/*
    아이템별 해당 재고가 있는 물류센터 목록
 */
public record CenterPossibleDTO(
        String productName,
        int quantity,
        String skuCode,
        List<String> availableCenterCodes
) {
}
