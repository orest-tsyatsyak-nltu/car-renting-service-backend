package com.example.carrentingservicebackend.service.impl;

import com.example.carrentingservicebackend.dto.AddTenantDTO;
import com.example.carrentingservicebackend.dto.GetTenantDTO;
import com.example.carrentingservicebackend.dto.UpdateTenantDTO;
import com.example.carrentingservicebackend.entity.TenantEntity;
import com.example.carrentingservicebackend.exception.NotFoundException;
import com.example.carrentingservicebackend.repository.TenantRepository;
import com.example.carrentingservicebackend.service.TenantService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public UUID registerTenant(AddTenantDTO tenantDto) {
        throwExceptionIfExist(tenantDto);
        TenantEntity tenant = modelMapper.map(tenantDto, TenantEntity.class);
        return tenantRepository.save(tenant).getId();
    }

    private void throwExceptionIfExist(AddTenantDTO tenantDTO) {
        String phoneNumber = tenantDTO.getPhoneNumber();
        try {
            getTenant(phoneNumber);
            throw new IllegalArgumentException(
                    "Tenant with phone number %s already exists".formatted(phoneNumber)
            );
        } catch (NotFoundException ignored) {
        }
    }

    @Override
    public List<GetTenantDTO> getTenants(Integer page, Integer pageSize) {
        return tenantRepository.findAll(PageRequest.of(page, pageSize)).stream()
                .map(this::tenantEntityToGetTenantDTO).toList();
    }

    private GetTenantDTO tenantEntityToGetTenantDTO(TenantEntity tenant) {
        return modelMapper.map(tenant, GetTenantDTO.class);
    }

    @Override
    public GetTenantDTO getTenant(String identifier) {
        return tenantEntityToGetTenantDTO(getRowTenant(identifier));
    }

    private TenantEntity getRowTenant(String identifier) {
        Optional<TenantEntity> tenant;
        try{
            tenant = tenantRepository.findById(UUID.fromString(identifier));
        } catch (IllegalArgumentException e) {
            tenant = tenantRepository.findByPhoneNumber(identifier);
        }
        return tenant.orElseThrow(
                () -> new NotFoundException("Tenant with identifier %s not found".formatted(identifier))
        );
    }

    @Override
    @Transactional
    public void updateTenant(String tenantIdentifier, UpdateTenantDTO tenantUpdates) {
        TenantEntity tenant = getRowTenant(tenantIdentifier);
        if(tenantUpdates.getAddress() != null) {
            tenant.setAddress(tenantUpdates.getAddress());
        }
        if(tenantUpdates.getFullName() != null) {
            tenant.setFullName(tenantUpdates.getFullName());
        }
        tenantRepository.flush();
    }

    @Override
    @Transactional
    public boolean deleteTenant(String tenantIdentifier) {
        try {
            TenantEntity tenant = getRowTenant(tenantIdentifier);
            tenantRepository.delete(tenant);
            tenantRepository.flush();
            return true;
        } catch (NotFoundException e) {
            return true;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
