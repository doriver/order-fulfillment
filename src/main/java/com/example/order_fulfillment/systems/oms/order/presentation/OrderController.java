package com.example.order_fulfillment.systems.oms.order.presentation;

import com.example.order_fulfillment.common.ApiResponse;
import com.example.order_fulfillment.systems.oms.order.application.OrderService;
import com.example.order_fulfillment.systems.oms.order.presentation.dto.OrderReceiveDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping
    public ApiResponse<Long> recieveOrder(@RequestBody OrderReceiveDTO dto) {
        Long savedOrderId = orderService.ingestionOrder(dto);
        return ApiResponse.success(savedOrderId);
    }
}
