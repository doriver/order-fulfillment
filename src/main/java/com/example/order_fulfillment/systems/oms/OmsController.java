package com.example.order_fulfillment.systems.oms;

import com.example.order_fulfillment.common.ApiResponse;
import com.example.order_fulfillment.systems.oms.order.application.OrderService;
import com.example.order_fulfillment.systems.integration.dto.OrderReceiveDTO;
import com.example.order_fulfillment.systems.integration.dto.OutboundDeliveryRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oms/api")
@RequiredArgsConstructor
public class OmsController {

    private final OrderService orderService;
    /*
        판매처의 결제완료된 주문 oms에서 받기
        (일단 api형태로 만들어놓고, 추후 변경 예정)
     */
    @Operation(summary = "판매처의 결제완료된 주문 oms에서 받기")
    @PostMapping("/order")
    public ApiResponse<Long> recieveOrder(@RequestBody OrderReceiveDTO dto) {
        Long savedOrderId = orderService.ingestionOrder(dto);
        return ApiResponse.success(savedOrderId);
    }


    /*
        특정 주문 이행을 위해, WMS,TMS에게 주문 데이터 전달
     */
    @GetMapping("/order/{id}/send")
    public ApiResponse<String> sendToWmsTms(@PathVariable("id") Long orderId) {
        orderService.sendToWmsTms(orderId);
        return ApiResponse.success();
    }

    /*
        출고, 배송 결정
        - wms로부터 받은 센터별 재고 가능 정보, tms로부터 받은 배송코스 정보를 바탕으로
          출고 센터와 배송 코스/회차를 결정
     */
    @Operation(summary = "출고 센터 및 배송 코스/회차 결정")
    @PostMapping("/order/outbound-delivery")
    public ApiResponse<String> determineOutboundDelivery(
            @RequestBody OutboundDeliveryRequestDTO dto
    ) {
        orderService.determineOutboundDelivery(
                dto.centerPossibles(),
                dto.routePossibles()
        );
        return ApiResponse.success();
    }
}


