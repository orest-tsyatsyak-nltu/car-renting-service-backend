package com.example.carrentingservicebackend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateCarDTO {
    private String brand;
    private String model;
    private String color;
    private BigDecimal pricePerDay;
    private CarStatus carStatus;
}
