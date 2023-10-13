package com.example.carrentingservicebackend.controller;

import com.example.carrentingservicebackend.dto.AddRentRecordDTO;
import com.example.carrentingservicebackend.dto.GetRentRecordDTO;
import com.example.carrentingservicebackend.service.RentRecordService;
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
@RequestMapping("/api/v1/rent-records")
@RequiredArgsConstructor
public class RentRecordController {

    private final RentRecordService rentRecordService;

    @GetMapping
    @Operation(description = "Returns rent records at specified page with specified page size. Page count starts from 0.")
    @ApiResponse(
            responseCode = "200",
            description = "Returns rent records that are at specified page with specified page size",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(
                            schema = @Schema(implementation = GetRentRecordDTO.class)
                    )
            )
    )
    public List<GetRentRecordDTO> getRentRecords(@RequestParam(required = false, defaultValue = "0")
                                     Integer page,
                                                 @RequestParam(required = false, defaultValue = "50")
                                     Integer pageSize) {
        return rentRecordService.getRentRecords(page, pageSize);
    }

    @GetMapping("/{rentRecordId}")
    @Operation(description = "Returns rent record by specified id. Returns 404 if no rent was found")
    @ApiResponse(
            responseCode = "200",
            description = "Returns rent record with specified id",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GetRentRecordDTO.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Rent record with specified id not found",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExceptionResponse.class)
            )
    )
    public GetRentRecordDTO getRentRecord(@PathVariable UUID rentRecordId) {
        GetRentRecordDTO rentRecord = rentRecordService.getRentRecord(rentRecordId);
        rentRecord.add(
                linkTo(methodOn(RentRecordController.class).getRentRecord(rentRecordId)).withSelfRel()
        );
        return rentRecord;
    }

    @PostMapping
    @Operation(
            description = "Adds given rent record to database."
    )
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(
            responseCode = "201",
            description = "Returns id of rent record that was added",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UUID.class)
            )
    )
    public UUID addRentRecord(@RequestBody AddRentRecordDTO addRentRecordDTO) {
        return rentRecordService.addRentRecord(addRentRecordDTO);
    }

}
