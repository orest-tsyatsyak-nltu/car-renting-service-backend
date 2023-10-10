package com.example.carrentingservicebackend.controller;

import com.example.carrentingservicebackend.dto.AddTenantDTO;
import com.example.carrentingservicebackend.dto.GetTenantDTO;
import com.example.carrentingservicebackend.dto.UpdateTenantDTO;
import com.example.carrentingservicebackend.service.TenantService;
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

@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @GetMapping
    @Operation(description = "Returns tenants at specified page with specified page size. Page count starts from 0.")
    @ApiResponse(
            responseCode = "200",
            description = "Returns tenants that are at specified page with specified page size",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(
                            schema = @Schema(implementation = GetTenantDTO.class)
                    )
            )
    )
    public List<GetTenantDTO> getTenants(@RequestParam(required = false, defaultValue = "0")
                                         Integer page,
                                         @RequestParam(required = false, defaultValue = "50")
                                         Integer pageSize) {
        return tenantService.getTenants(page, pageSize);
    }

    @GetMapping("/{tenantIdentifier}")
    @Operation(description = "Returns tenant by specified id or phone number. Returns 404 if no tenant was found")
    @ApiResponse(
            responseCode = "200",
            description = "Returns tenant with specified id or phone number",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = GetTenantDTO.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Tenant with specified id or phone number was not found",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExceptionResponse.class)
            )
    )
    public GetTenantDTO getTenant(@PathVariable String tenantIdentifier) {
        return tenantService.getTenant(tenantIdentifier);
    }

    @PostMapping
    @Operation(
            description = "Registers given tenant to database." +
                    " Returns 400 if car with given phone number already exists"
    )
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(
            responseCode = "201",
            description = "Returns id of registered tenant",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UUID.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Means that phone number already exist",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ExceptionResponse.class)
            )
    )
    public UUID registerTenant(@RequestBody AddTenantDTO addTenantDto) {
        return tenantService.registerTenant(addTenantDto);
    }

    @PutMapping("/{tenantIdentifier}")
    @Operation(
            description = "Updates tenant with given identifier with data from UpdateTenantDTO." +
                    " If UpdateTenantDTO has null fields then that specific field will not be effected"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Means that tenant was updated and all given parameters were changed"
    )
    public void updateTenant(@PathVariable String tenantIdentifier, @RequestBody UpdateTenantDTO updateTenantDTO) {
        tenantService.updateTenant(tenantIdentifier, updateTenantDTO);
    }

    @DeleteMapping("/{tenantIdentifier}")
    @Operation(
            description = "Deletes tenant with given identifier, returns true if tenant was deleted," +
                    " or there was no tenant with such identifier, false if something gone wrong"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Means that tenant was deleted, or there was no tenant with such identifier"
    )
    public boolean deleteTenant(@PathVariable String tenantIdentifier) {
        return tenantService.deleteTenant(tenantIdentifier);
    }

}
