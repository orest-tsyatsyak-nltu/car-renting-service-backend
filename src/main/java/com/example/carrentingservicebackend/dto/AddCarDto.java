package com.example.carrentingservicebackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddCarDto {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @NotBlank
    private String registrationNumber;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @NotBlank
    private String brand;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @NotBlank
    private String model;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @NotBlank
    private String color;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private BigDecimal pricePerDay;
}
