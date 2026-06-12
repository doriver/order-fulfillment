package com.example.order_fulfillment.systems.dto;

import com.example.order_fulfillment.systems.oms.order.domain.entity.OrderItem;

import java.util.List;
/*
    oms에서 출고, 배송의 결정을 위해 wms에게 전달하는 order관련 데이터
 */
public record WmsOrderDTO(
        List<Item> items
) {
    public record Item(
            String productName,
            String skuOmsCode,
            int quantity
    ) {
        public static Item from(OrderItem orderItem) {
            return new Item(
                    orderItem.getProductName(),
                    orderItem.getSkuOms().getCode(),
                    orderItem.getQuantity()
            );
        }
    }
}
