package com.example.carrentingservicebackend.repository;

import com.example.carrentingservicebackend.entity.RentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RentRepository extends JpaRepository<RentEntity, UUID> {

    Optional<RentEntity> findByCarRegistrationNumber(String carRegistrationNumber);

}
