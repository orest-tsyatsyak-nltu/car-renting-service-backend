package com.example.carrentingservicebackend.service;

import com.example.carrentingservicebackend.dto.AddRentRecordDTO;
import com.example.carrentingservicebackend.dto.GetRentRecordDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Validated
public interface RentRecordService {

    UUID addRentRecord(@Valid AddRentRecordDTO rentDto);

    List<GetRentRecordDTO> getRentRecords(@Min(0) Integer page, @Min(1) @Max(200) Integer pageSize);

    GetRentRecordDTO getRentRecord(UUID id);

}
