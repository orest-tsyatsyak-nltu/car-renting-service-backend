package com.example.carrentingservicebackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddRentRecordDTO {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @NotBlank
    private String rentIdentifier;

    @Schema(allowableValues = {"AVAILABLE", "MAINTENANCE"})
    private CarStatus returnCarStatus;

}
