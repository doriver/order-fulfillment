package com.example.order_fulfillment.systems.integration.mdm.register.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record DeliveryRouteRegisterDTO(
        @NotBlank @Size(max = 20) String code,
        @NotBlank @Size(max = 100) String name,
        @NotEmpty @Valid List<NodeDTO> nodes
) {
    public record NodeDTO(
            @NotBlank String zoneCode,
            @Min(1) int sequence
    ) {}
}
