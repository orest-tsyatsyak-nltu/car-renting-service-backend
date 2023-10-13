package com.example.carrentingservicebackend.controller;

import com.example.carrentingservicebackend.dto.AddRentDTO;
import com.example.carrentingservicebackend.dto.GetRentDTO;
import com.example.carrentingservicebackend.service.RentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/rents")
@RequiredArgsConstructor
public class RentController {

    private final RentService rentService;

    @GetMapping
    @Operation(description = "Returns rents at specified page with specified page size. Page count starts from 0.")
    @ApiResponse(
            responseCode = "200",
            description = "Returns rents that are at specified page with specified page size",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(
                            schema = @Schema(implementation = GetRentDTO.class)
                    )
            )
    )
    public List<GetRentDTO> getRents(@RequestParam(required = false, defaultValue = "0")
                                   Integer page,
                                   @RequestParam(required = false, defaultValue = "50")
                                   Integer pageSize) {
        return rentService.getRents(page, pageSize);
    }

    @GetMapping("/{rentIdentifier}")
    @Operation(description = "Returns rent by specified id or car registration number. Returns 404 if no rent was found")
    @ApiResponse(
            responseCode = "200",
            description = "Returns rent with specified id or car registration number",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GetRentDTO.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Rent with specified id or car registration number was not found",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExceptionResponse.class)
            )
    )
    public GetRentDTO getRent(@PathVariable String rentIdentifier) {
        GetRentDTO rent = rentService.getRent(rentIdentifier);
        rent.add(
                linkTo(methodOn(RentController.class).getRent(rentIdentifier)).withSelfRel()
        );
        return rent;
    }

    @PostMapping
    @Operation(
            description = "Adds given Rent to database. Returns 400 if rent with car given registration number already exists"
    )
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(
            responseCode = "201",
            description = "Returns id of rent that was registered",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UUID.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Means that car registration number is already taken",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExceptionResponse.class)
            )
    )
    public UUID registerRent(@RequestBody AddRentDTO addRentDTO) {
        return rentService.registerRent(addRentDTO);
    }

    @DeleteMapping("/{rentIdentifier}")
    @Operation(
            description = "Deletes rent with given identifier, returns true if rent was deleted," +
                    " or there was no rent with such identifier, false if something gone wrong"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Means that rent was deleted, or there was no rent with such identifier"
    )
    public boolean deleteRent(@PathVariable String rentIdentifier) {
        return rentService.deleteRent(rentIdentifier);
    }

}
