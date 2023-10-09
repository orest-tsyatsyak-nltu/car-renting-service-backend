package com.example.carrentingservicebackend.entity;

import com.example.carrentingservicebackend.dto.CarStatus;
import jakarta.persistence.*;
import lombok.*;

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
