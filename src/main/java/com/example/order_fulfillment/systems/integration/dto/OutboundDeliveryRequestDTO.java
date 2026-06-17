package com.example.order_fulfillment.systems.integration.dto;

import java.util.List;

/*
    출고/배송 결정 요청 (TMS의 배송코스 후보 + WMS의 재고가능 센터 후보)
 */
public record OutboundDeliveryRequestDTO(
        List<OrderRoutePossibleDTO> routePossibles,
        List<CenterPossibleDTO> centerPossibles
) {}
