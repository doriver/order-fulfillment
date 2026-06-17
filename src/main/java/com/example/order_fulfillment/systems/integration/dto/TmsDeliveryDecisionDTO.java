package com.example.order_fulfillment.systems.integration.dto;

import java.util.List;

/*
    oms에서 배송 결정후 tms에게 전달하는 데이터
    - 어떤 배송코스의 몇 회차에, 어떤 센터들에서 상차할지
    실제로 tms가 이행하는데 필요한 데이터들 추가로 담아야함(주문 관련 데이터 등등)
 */
public record TmsDeliveryDecisionDTO(
        String routeCode,
        int roundNumber,
        List<String> loadingCenterCodes
) {}
