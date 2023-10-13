package com.example.carrentingservicebackend.service;

import com.example.carrentingservicebackend.dto.AddRentDTO;
import com.example.carrentingservicebackend.dto.GetRentDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Validated
public interface RentService {

    UUID registerRent(@Valid AddRentDTO rentDto);

    List<GetRentDTO> getRents(@Min(0) Integer page, @Min(1) @Max(200) Integer pageSize);

    GetRentDTO getRent(String identifier);

    boolean deleteRent(String rentIdentifier);

}
