package com.example.carrentingservicebackend.controller;

import com.example.carrentingservicebackend.dto.AddCarDTO;
import com.example.carrentingservicebackend.dto.GetCarDTO;
import com.example.carrentingservicebackend.dto.UpdateCarDTO;
import com.example.carrentingservicebackend.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping
    @Operation(description = "Returns cars at specified page with specified page size. Page count starts from 0.")
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
    public List<GetCarDTO> getCars(@RequestParam(required = false, defaultValue = "0")
                                   Integer page,
                                   @RequestParam(required = false, defaultValue = "50")
                                   Integer pageSize) {
        return carService.getCars(page, pageSize);
    }

    @GetMapping("/{carIdentifier}")
    @Operation(description = "Returns car by specified id or registration number. Returns 404 if no car was found")
    @ApiResponse(
            responseCode = "200",
            description = "Returns car with specified car id or car registration number",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GetCarDTO.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Car with specified car id or car registration number was not found",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExceptionResponse.class)
            )
    )
    public GetCarDTO getCar(@PathVariable String carIdentifier) {
        GetCarDTO car = carService.getCar(carIdentifier);
        car.add(getLinksForCar(car));
        return car;
    }

    private List<Link> getLinksForCar(GetCarDTO car){
        return List.of(
                linkTo(methodOn(CarController.class).getCar(car.getId().toString())).withSelfRel(),
                linkTo(methodOn(CarController.class).addCar(null)).withRel("add-car")
        );
    }

    @PostMapping
    @Operation(
            description = "Adds given car to database. Returns 400 if car with given registration number already exists"
    )
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(
            responseCode = "201",
            description = "Returns id of car that was added",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UUID.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Means that registration number already exist",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExceptionResponse.class)
            )
    )
    public ResponseEntity<UUID> addCar(@RequestBody AddCarDTO addCarDto) {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(carService.addCar(addCarDto));
    }

    @PutMapping("/{carIdentifier}")
    @Operation(
            description = "Updates car with given identifier with data from UpdateCarDTO." +
                    " If UpdateCarDTO has null fields then that specific field will not be effected"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Means that car was updated and all given parameters were changed"
    )
    public void updateCar(@PathVariable String carIdentifier, @RequestBody UpdateCarDTO updateCarDTO) {
        carService.updateCar(carIdentifier, updateCarDTO);
    }

    @DeleteMapping("/{carIdentifier}")
    @Operation(
            description = "Deletes car with given identifier, returns true if car was deleted," +
                    " or there was no car with such identifier, false if something gone wrong"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Means that car was deleted, or there was no car with such identifier"
    )
    public boolean deleteCar(@PathVariable String carIdentifier) {
        return carService.deleteCar(carIdentifier);
    }

}
