package com.example.carrentingservicebackend.controller;

import com.example.carrentingservicebackend.dto.AddCarDTO;
import com.example.carrentingservicebackend.dto.AddRentDTO;
import com.example.carrentingservicebackend.dto.AddRentRecordDTO;
import com.example.carrentingservicebackend.dto.AddTenantDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/")
public class MainController {

    @GetMapping
    @Operation(description = "Returns links for all major endpoints")
    public RepresentationModel mainEndpoint() {
        return new RepresentationModel().add(
                linkTo(methodOn(MainController.class).mainEndpoint()).withSelfRel(),
                linkTo(methodOn(CarController.class).getCars(0, 10)).withRel("get-cars"),
                linkTo(methodOn(TenantController.class).getTenants(0, 10)).withRel("get-tenants"),
                linkTo(methodOn(RentController.class).getRents(0, 10)).withRel("get-rents"),
                linkTo(methodOn(RentRecordController.class).getRentRecords(0, 10)).withRel("get-rent-records")
        );
    }

}
