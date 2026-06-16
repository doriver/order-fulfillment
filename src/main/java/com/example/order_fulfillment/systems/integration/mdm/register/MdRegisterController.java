package com.example.order_fulfillment.systems.integration.mdm.register;

import com.example.order_fulfillment.common.ApiResponse;
import com.example.order_fulfillment.systems.integration.mdm.register.dto.ChannelRegisterDTO;
import com.example.order_fulfillment.systems.integration.mdm.register.dto.DeliveryRouteRegisterDTO;
import com.example.order_fulfillment.systems.integration.mdm.register.dto.LogisticsCenterRegisterDTO;
import com.example.order_fulfillment.systems.integration.mdm.register.dto.SkuRegisterDTO;
import com.example.order_fulfillment.systems.integration.mdm.register.dto.ZoneRegisterDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
/*
    master data 등록
 */
@RestController
@RequestMapping("/mdm/api")
@RequiredArgsConstructor
public class MdRegisterController {

    private final MdRegisterService registerService;

    // 판매처 등록
    @PostMapping("/channels")
    public ApiResponse<Long> registerChannel(@RequestBody @Valid ChannelRegisterDTO dto) {
        return ApiResponse.success(registerService.saveChannel(dto), HttpStatus.CREATED);
    }

    // 권역 등록
    @PostMapping("/zones")
    public ApiResponse<String> registerZone(@RequestBody @Valid ZoneRegisterDTO dto) {
        return ApiResponse.success(registerService.saveZone(dto), HttpStatus.CREATED);
    }

    // 물류센터 등록
    @PostMapping("/logistics-centers")
    public ApiResponse<String> registerLogisticsCenter(@RequestBody @Valid LogisticsCenterRegisterDTO dto) {
        return ApiResponse.success(registerService.saveLogisticsCenter(dto), HttpStatus.CREATED);
    }

    // 배송 코스 등록
    @PostMapping("/delivery-routes")
    public ApiResponse<String> registerDeliveryRoute(@RequestBody @Valid DeliveryRouteRegisterDTO dto) {
        return ApiResponse.success(registerService.saveDeliveryRoute(dto), HttpStatus.CREATED);
    }

    // sku 등록
    @PostMapping("/skus")
    public ApiResponse<String> registerSku(@RequestBody @Valid SkuRegisterDTO dto) {
        return ApiResponse.success(registerService.saveSku(dto), HttpStatus.CREATED);
    }

}
