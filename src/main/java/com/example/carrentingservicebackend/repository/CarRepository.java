package com.example.carrentingservicebackend.repository;

import com.example.carrentingservicebackend.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, UUID> {

    Optional<CarEntity> findByRegistrationNumber(String registrationNumber);

}
