package com.example.carrentingservicebackend.service.impl;

import com.example.carrentingservicebackend.dto.*;
import com.example.carrentingservicebackend.entity.RentRecordEntity;
import com.example.carrentingservicebackend.exception.NotFoundException;
import com.example.carrentingservicebackend.repository.RentRecordRepository;
import com.example.carrentingservicebackend.service.CarService;
import com.example.carrentingservicebackend.service.DateTimeService;
import com.example.carrentingservicebackend.service.RentRecordService;
import com.example.carrentingservicebackend.service.RentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RentRecordServiceImpl implements RentRecordService {

    private static final double NUMBER_OF_SECONDS_IN_DAY = 86400D;

    private final RentRecordRepository rentRecordRepository;

    private final ModelMapper modelMapper;

    private final DateTimeService dateTimeService;

    private final RentService rentService;

    private final CarService carService;

    @Override
    @Transactional
    public UUID addRentRecord(AddRentRecordDTO rentRecordDto) {
        GetRentDTO rent = rentService.getRent(rentRecordDto.getRentIdentifier());
        LocalDateTime now = dateTimeService.now();
        Integer delayInDays = calculateDelay(rent, now);

        RentRecordEntity rentRecordEntity = new RentRecordEntity();
        rentRecordEntity.setReturnDate(now);
        rentRecordEntity.setCarRegistrationNumber(rent.getCarRegistrationNumber());
        rentRecordEntity.setTenantPhoneNumber(rent.getTenantPhoneNumber());
        rentRecordEntity.setDelayInDays(delayInDays);
        rentRecordEntity.setTotalPayment(calculateTotalPayment(rent, delayInDays));

        rentService.deleteRent(rent.getCarRegistrationNumber());
        freeTheCar(rentRecordDto, rent);

        return rentRecordRepository.save(rentRecordEntity).getId();
    }

    private Integer calculateDelay(GetRentDTO rent, LocalDateTime now) {
        ZoneOffset zoneOffset = ZoneId.systemDefault().getRules().getOffset(now);
        long nowSeconds = now.toInstant(zoneOffset).getEpochSecond();
        long returnSeconds = rent.getReturnDate().toInstant(zoneOffset).getEpochSecond();
        return Math.toIntExact(Math.round((nowSeconds - returnSeconds) / NUMBER_OF_SECONDS_IN_DAY));
    }

    private BigDecimal calculateTotalPayment(GetRentDTO rent, Integer delayInDays) {
        if (delayInDays == 0) {
            return rent.getFinalPrice();
        }
        BigDecimal carPricePerDay = carService.getCar(rent.getCarRegistrationNumber()).getPricePerDay();
        return rent.getFinalPrice().add(carPricePerDay.multiply(BigDecimal.valueOf(delayInDays)));
    }

    private void freeTheCar(AddRentRecordDTO rentRecordDto, GetRentDTO rent) {
        CarStatus returnCarStatus = rentRecordDto.getReturnCarStatus();
        if(returnCarStatus.equals(CarStatus.RENTED)) {
            throw new IllegalArgumentException("When returning car from rent you can not set car`s status to rented.");
        }
        carService.updateCar(
                rent.getCarRegistrationNumber(),
                UpdateCarDTO.builder().carStatus(returnCarStatus).build()
        );
    }

    @Override
    public List<GetRentRecordDTO> getRentRecords(Integer page, Integer pageSize) {
        return rentRecordRepository.findAll(PageRequest.of(page, pageSize))
                .stream().map(this::rentRecordEntityToGetRentRecordDTO).toList();
    }

    private GetRentRecordDTO rentRecordEntityToGetRentRecordDTO(RentRecordEntity car) {
        return modelMapper.map(car, GetRentRecordDTO.class);
    }

    @Override
    public GetRentRecordDTO getRentRecord(UUID id) {
        return rentRecordEntityToGetRentRecordDTO(rentRecordRepository.findById(id).
                orElseThrow(()-> new NotFoundException("Rent record with id %s not found".formatted(id)))
        );
    }

}
