package com.example.order_fulfillment.systems.oms.order.presentation;

import com.example.order_fulfillment.common.ApiResponse;
import com.example.order_fulfillment.systems.oms.order.application.OrderService;
import com.example.order_fulfillment.systems.integration.dto.OrderReceiveDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oms/api")
@RequiredArgsConstructor
public class OrderController {

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
}
