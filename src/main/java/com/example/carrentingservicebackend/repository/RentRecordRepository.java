package com.example.carrentingservicebackend.repository;

import com.example.carrentingservicebackend.entity.RentRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RentRecordRepository extends JpaRepository<RentRecordEntity, UUID> {
}
