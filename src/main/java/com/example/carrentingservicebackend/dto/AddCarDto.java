package com.example.carrentingservicebackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddCarDto {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private String registrationNumber;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private String brand;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private String model;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private String color;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private BigDecimal pricePerDay;
}
