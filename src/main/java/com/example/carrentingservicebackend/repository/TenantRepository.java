package com.example.carrentingservicebackend.repository;

import com.example.carrentingservicebackend.entity.TenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TenantRepository extends JpaRepository<TenantEntity, UUID> {

    Optional<TenantEntity> findByPhoneNumber(String phoneNumber);

}
