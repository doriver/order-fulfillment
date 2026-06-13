package com.example.order_fulfillment.systems.integration.mdm.register;

import com.example.order_fulfillment.common.ApiResponse;
import com.example.order_fulfillment.systems.integration.mdm.register.dto.ChannelRegisterDTO;
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

    public void registerLogisticsCenter() {

    }

    public void registerDeliveryRoute() {

    }

}
