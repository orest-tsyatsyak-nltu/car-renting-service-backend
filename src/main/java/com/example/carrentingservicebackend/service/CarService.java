package com.example.carrentingservicebackend.service;

import com.example.carrentingservicebackend.dto.AddCarDto;
import com.example.carrentingservicebackend.dto.GetCarDTO;
import com.example.carrentingservicebackend.dto.UpdateCarDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Validated
public interface CarService {
    UUID addCar(@Valid AddCarDto carDto);

    List<GetCarDTO> getCars(@Min(0) Integer page, @Min(1) @Max(200) Integer pageSize);

    GetCarDTO getCar(String identifier);

    void updateCar(String carIdentifier, UpdateCarDTO carUpdates);

    boolean deleteCar(String carIdentifier);

}
