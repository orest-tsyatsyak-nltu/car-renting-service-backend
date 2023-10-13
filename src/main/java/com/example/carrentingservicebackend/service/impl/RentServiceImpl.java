package com.example.carrentingservicebackend.service.impl;

import com.example.carrentingservicebackend.dto.AddRentDTO;
import com.example.carrentingservicebackend.dto.GetCarDTO;
import com.example.carrentingservicebackend.dto.GetRentDTO;
import com.example.carrentingservicebackend.entity.RentEntity;
import com.example.carrentingservicebackend.exception.NotFoundException;
import com.example.carrentingservicebackend.repository.RentRepository;
import com.example.carrentingservicebackend.service.CarService;
import com.example.carrentingservicebackend.service.RentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RentServiceImpl implements RentService {

    private final RentRepository rentRepository;

    private final ModelMapper modelMapper;

    private final CarService carService;

    @Override
    @Transactional
    public UUID registerRent(AddRentDTO rentDto) {
        throwExceptionIfExist(rentDto);
        throwExceptionIfRentDateIsAfterReturnDate(rentDto);
        RentEntity rent = modelMapper.map(rentDto, RentEntity.class);
        rent.setFinalPrice(calculateFinalPrice(rent));
        return rentRepository.save(rent).getId();
    }

    private void throwExceptionIfExist(AddRentDTO rentDto) {
        String registrationNumber = rentDto.getCarRegistrationNumber();
        try {
            getRent(registrationNumber);
            throw new IllegalArgumentException(
                    "Car with registration number %s is already in rent".formatted(registrationNumber)
            );
        } catch (NotFoundException ignored) {
        }
    }

    private void throwExceptionIfRentDateIsAfterReturnDate(AddRentDTO rentDto) {
        if(rentDto.getRentDate().isAfter(rentDto.getReturnDate())) {
            throw new IllegalArgumentException("Rent date can not be after return date.");
        }
    }

    private BigDecimal calculateFinalPrice(RentEntity rent) {
        LocalDateTime returnDate = rent.getReturnDate();
        LocalDateTime rentDate = rent.getRentDate();
        int daysInRent = returnDate.minusYears(rentDate.getYear())
                .minusDays(rentDate.getDayOfYear()).getDayOfYear();
        GetCarDTO rentedCar = carService.getCar(rent.getCarRegistrationNumber());
        return rentedCar.getPricePerDay().multiply(BigDecimal.valueOf(daysInRent));
    }

    @Override
    public List<GetRentDTO> getRents(Integer page, Integer pageSize) {
        return rentRepository.findAll(PageRequest.of(page, pageSize)).get()
                .map(this::rentEntityToGetRentDTO).toList();
    }

    private GetRentDTO rentEntityToGetRentDTO(RentEntity car) {
        return modelMapper.map(car, GetRentDTO.class);
    }

    @Override
    public GetRentDTO getRent(String identifier) {
        return rentEntityToGetRentDTO(getRowRent(identifier));
    }

    private RentEntity getRowRent(String identifier) {
        Optional<RentEntity> rent;
        try{
            rent = rentRepository.findById(UUID.fromString(identifier));
        } catch (IllegalArgumentException e) {
            rent = rentRepository.findByCarRegistrationNumber(identifier);
        }
        return rent.orElseThrow(
                () -> new NotFoundException("Rent with identifier %s not found".formatted(identifier))
        );
    }

    @Override
    @Transactional
    public boolean deleteRent(String rentIdentifier) {
        try {
            RentEntity rent = getRowRent(rentIdentifier);
            rentRepository.delete(rent);
            rentRepository.flush();
            return true;
        } catch (NotFoundException e) {
            return true;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
