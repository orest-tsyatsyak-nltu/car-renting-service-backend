package com.example.carrentingservicebackend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode
public class UpdateCarDTO {
    private String brand;
    private String model;
    private String color;
    private BigDecimal pricePerDay;
    private CarStatus carStatus;
}
