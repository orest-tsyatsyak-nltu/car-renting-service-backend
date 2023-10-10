package com.example.carrentingservicebackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AddTenantDTO {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @NotBlank
    private String fullName;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @Length(max = 15)
    @NotBlank
    private String phoneNumber;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @NotBlank
    private String address;
}
