package com.example.order_fulfillment.systems.integration.mdm.register.dto;

import com.example.order_fulfillment.systems.oms.zone.domain.entity.RegionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ZoneRegisterDTO(
        @NotBlank @Size(max = 20) String code,
        @NotBlank @Size(max = 100) String region,
        @NotNull RegionType regionType,
        String parentCode // 최상위 권역이면 null
) {}
