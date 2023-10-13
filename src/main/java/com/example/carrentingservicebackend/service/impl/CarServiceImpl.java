package com.example.carrentingservicebackend.service.impl;

import com.example.carrentingservicebackend.dto.AddCarDTO;
import com.example.carrentingservicebackend.dto.CarStatus;
import com.example.carrentingservicebackend.dto.GetCarDTO;
import com.example.carrentingservicebackend.dto.UpdateCarDTO;
import com.example.carrentingservicebackend.entity.CarEntity;
import com.example.carrentingservicebackend.exception.NotFoundException;
import com.example.carrentingservicebackend.repository.CarRepository;
import com.example.carrentingservicebackend.service.CarService;
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
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public UUID addCar(AddCarDTO carDto) {
        throwExceptionIfExist(carDto);
        CarEntity car = modelMapper.map(carDto, CarEntity.class);
        car.setCarStatus(CarStatus.AVAILABLE);
        return carRepository.save(car).getId();
    }

    private void throwExceptionIfExist(AddCarDTO carDto) {
        String registrationNumber = carDto.getRegistrationNumber();
        try {
            getCar(registrationNumber);
            throw new IllegalArgumentException(
                    "Car with registration number %s already exists".formatted(registrationNumber)
            );
        } catch (NotFoundException ignored) {
        }
    }

    @Override
    public List<GetCarDTO> getCars(Integer page, Integer pageSize) {
        return carRepository.findAll(PageRequest.of(page, pageSize)).get()
                .map(this::carEntityToGetCarDTO).toList();
    }

    private GetCarDTO carEntityToGetCarDTO(CarEntity car) {
        return modelMapper.map(car, GetCarDTO.class);
    }

    @Override
    public GetCarDTO getCar(String identifier) {
        return carEntityToGetCarDTO(getRowCar(identifier));
    }

    private CarEntity getRowCar(String identifier) {
        Optional<CarEntity> car;
        try{
            car = carRepository.findById(UUID.fromString(identifier));
        } catch (IllegalArgumentException e) {
            car = carRepository.findByRegistrationNumber(identifier);
        }
        return car.orElseThrow(
                        () -> new NotFoundException("Car with identifier %s not found".formatted(identifier))
                );
    }

    @Override
    @Transactional
    public void updateCar(String carIdentifier, UpdateCarDTO carUpdates) {
        CarEntity car = getRowCar(carIdentifier);
        if(carUpdates.getBrand() != null) {
            car.setBrand(carUpdates.getBrand());
        }
        if(carUpdates.getModel() != null) {
            car.setModel(carUpdates.getModel());
        }
        if(carUpdates.getColor() != null) {
            car.setColor(carUpdates.getColor());
        }
        if(carUpdates.getPricePerDay() != null) {
            car.setPricePerDay(carUpdates.getPricePerDay());
        }
        if(carUpdates.getCarStatus() != null) {
            car.setCarStatus(carUpdates.getCarStatus());
        }
        carRepository.flush();
    }

    @Override
    @Transactional
    public boolean deleteCar(String carIdentifier) {
        try {
            CarEntity car = getRowCar(carIdentifier);
            carRepository.delete(car);
            carRepository.flush();
            return true;
        } catch (NotFoundException e) {
            return true;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

}
