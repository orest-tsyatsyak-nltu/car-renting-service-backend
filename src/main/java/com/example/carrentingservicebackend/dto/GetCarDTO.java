package com.example.carrentingservicebackend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class GetCarDTO extends RepresentationModel<GetCarDTO> {
    private UUID id;
    private String registrationNumber;
    private String brand;
    private String model;
    private String color;
    private BigDecimal pricePerDay;
    private CarStatus carStatus;
}
