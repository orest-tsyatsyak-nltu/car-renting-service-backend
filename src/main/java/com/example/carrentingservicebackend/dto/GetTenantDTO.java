package com.example.carrentingservicebackend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class GetTenantDTO extends RepresentationModel<GetTenantDTO> {
    private UUID id;
    private String fullName;
    private String address;
    private String phoneNumber;
}
