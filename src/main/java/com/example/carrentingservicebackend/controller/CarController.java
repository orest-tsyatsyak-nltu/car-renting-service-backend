package com.example.carrentingservicebackend.controller;

import com.example.carrentingservicebackend.dto.AddCarDto;
import com.example.carrentingservicebackend.dto.GetCarDTO;
import com.example.carrentingservicebackend.service.CarService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping(produces = "application/vnd.collection+json")
    @ApiResponse(
            responseCode = "200",
            description = "Returns cars that are at specified page with specified page size",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(
                            schema = @Schema(implementation = GetCarDTO.class)
                    )
            )
    )
    public List<GetCarDTO> getCars(@RequestParam(required = false, defaultValue = "1")
                                   Integer page,
                                   @RequestParam(required = false, defaultValue = "50")
                                   Integer pageSize) {
        return carService.getCars(page, pageSize);
    }

    @GetMapping("/{id}")
    public GetCarDTO getCar(@PathVariable UUID id) {
        return carService.getCar(id);
    }

    @PostMapping
    public UUID addCar(@RequestBody AddCarDto addCarDto) {
        return carService.addCar(addCarDto);
    }

}
