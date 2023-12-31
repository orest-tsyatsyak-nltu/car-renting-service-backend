package com.example.carrentingservicebackend.entity;

import com.example.carrentingservicebackend.dto.CarStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Entity(name = "CarEntity")
@Table(
        name = "cars",
        uniqueConstraints = @UniqueConstraint(
                name = "cars_registration_number_key",
                columnNames = "registration_number"
        )
)
@Getter
@Setter
@ToString
@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CarEntity {
    @Id
    @Column(name = "id", nullable = false, columnDefinition="binary(16) not null")
    @GeneratedValue
    private UUID id;

    @Column(
            name = "registration_number",
            nullable = false,
            columnDefinition = "varchar(100)"
    )
    private String registrationNumber;

    @Column(
            name = "brand",
            nullable = false,
            columnDefinition = "varchar(100)"
    )
    private String brand;

    @Column(
            name = "model",
            nullable = false,
            columnDefinition = "varchar(100)"
    )
    private String model;

    @Column(
            name = "color",
            nullable = false,
            columnDefinition = "varchar(100)"
    )
    private String color;

    @Column(
            name = "price_per_day",
            nullable = false,
            columnDefinition = "decimal"
    )
    private BigDecimal pricePerDay;
    @Column(
            name = "car_status",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private CarStatus carStatus;
    @Version
    private Long version;
}
