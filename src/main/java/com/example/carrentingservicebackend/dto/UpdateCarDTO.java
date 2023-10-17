package com.example.carrentingservicebackend.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UpdateCarDTO {
    private String brand;
    private String model;
    private String color;
    private BigDecimal pricePerDay;
    private CarStatus carStatus;
}
