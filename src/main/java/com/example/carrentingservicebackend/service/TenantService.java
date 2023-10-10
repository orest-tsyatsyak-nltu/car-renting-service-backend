package com.example.carrentingservicebackend.service;

import com.example.carrentingservicebackend.dto.AddTenantDTO;
import com.example.carrentingservicebackend.dto.GetTenantDTO;
import com.example.carrentingservicebackend.dto.UpdateTenantDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Validated
public interface TenantService {

    UUID registerTenant(@Valid AddTenantDTO tenantDto);

    List<GetTenantDTO> getTenants(@Min(0) Integer page, @Min(1) @Max(200) Integer pageSize);

    GetTenantDTO getTenant(String identifier);

    void updateTenant(String tenantIdentifier, UpdateTenantDTO tenantUpdates);

    boolean deleteTenant(String tenantIdentifier);

}
