package com.example.order_fulfillment.systems.tms;

import com.example.order_fulfillment.common.ApiResponse;
import com.example.order_fulfillment.systems.integration.dto.OrderRoutePossibleDTO;
import com.example.order_fulfillment.systems.integration.dto.TmsOrderDTO;
import com.example.order_fulfillment.systems.tms.route.application.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tms/api")
@RequiredArgsConstructor
public class TmsController {

    private final RouteService routeService;

    /*
        배송지에 맞는 배송코스들의 회차, 상차가능 센터들을 응답
     */
    @GetMapping("/order/route")
    public ApiResponse<List<OrderRoutePossibleDTO>> orderJudgment(@RequestBody TmsOrderDTO dto) {
        List<OrderRoutePossibleDTO> orderRoutePossibleList = routeService.routeJudgment(dto);
        return ApiResponse.success(orderRoutePossibleList);
    }
}
