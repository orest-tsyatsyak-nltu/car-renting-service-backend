package com.example.carrentingservicebackend.service.impl;

import com.example.carrentingservicebackend.dto.AddCarDto;
import com.example.carrentingservicebackend.dto.CarStatus;
import com.example.carrentingservicebackend.dto.GetCarDTO;
import com.example.carrentingservicebackend.entity.CarEntity;
import com.example.carrentingservicebackend.repository.CarRepository;
import com.example.carrentingservicebackend.service.CarService;
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
    public UUID addCar(AddCarDto carDto) {
        CarEntity car = modelMapper.map(carDto, CarEntity.class);
        car.setCarStatus(CarStatus.AVAILABLE);
        return carRepository.save(car).getId();
    }

    @Override
    public List<GetCarDTO> getCars(Integer page, Integer pageSize) {
        return carRepository.findAll(PageRequest.of(page, pageSize)).get()
                .map(this::carEntiryToGetCarDTO).toList();
    }

    @Override
    public GetCarDTO getCar(UUID id) {
        Optional<CarEntity> byId = carRepository.findById(id);
        return carEntiryToGetCarDTO(byId.orElseThrow(
                () -> new IllegalArgumentException("Car with id %s not found".formatted(id)))
        );
    }

    private GetCarDTO carEntiryToGetCarDTO(CarEntity car) {
        return modelMapper.map(car, GetCarDTO.class);
    }

}
