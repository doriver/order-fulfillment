package com.example.order_fulfillment.systems.integration.mdm.register.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChannelRegisterDTO(
        @NotBlank @Size(max = 50) String code,
        @NotBlank @Size(max = 100) String name
) {}
