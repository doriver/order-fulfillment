package com.example.order_fulfillment.systems.integration.dto;

import java.util.List;

/*
    oms에서 출고 결정후 wms에게 전달하는 데이터
    - 아이템별 출고 센터 (한 주문에 출고 센터가 여러개일 수 있음)
    실제로 wms가 이행하는데 필요한 데이터들 추가로 담아야함(주문 관련 데이터 등등)
 */
public record WmsOutboundDecisionDTO(
        List<Item> items
) {
    public record Item(String skuCode, int quantity, String centerCode) {}
}
