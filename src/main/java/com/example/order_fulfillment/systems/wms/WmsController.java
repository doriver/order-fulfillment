package com.example.order_fulfillment.systems.wms;

import com.example.order_fulfillment.common.ApiResponse;
import com.example.order_fulfillment.systems.integration.dto.WmsOrderDTO;
import com.example.order_fulfillment.systems.wms.stock.application.StockService;
import com.example.order_fulfillment.systems.integration.dto.CenterPossibleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wms/api")
@RequiredArgsConstructor
public class WmsController {

    private final StockService stockService;

    /*
        아이템별 해당 재고가 있는 물류센터 목록을 응답
     */
    @GetMapping("/order/center")
    public ApiResponse<List<CenterPossibleDTO>> orderJudgment(@RequestBody WmsOrderDTO dto) {
        List<CenterPossibleDTO> centerPossibleList = stockService.centerJudgment(dto);
        return ApiResponse.success(centerPossibleList);
    }

}
