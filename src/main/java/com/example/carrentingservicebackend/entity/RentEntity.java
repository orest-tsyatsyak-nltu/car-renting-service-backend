package com.example.carrentingservicebackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "RentEntity")
@Table(
        name = "rents",
        uniqueConstraints = @UniqueConstraint(
                name = "car_registration_number_key",
                columnNames = "car_registration_number"
        )
)
@Getter
@Setter
@ToString
@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RentEntity {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "binary(16) not null")
    @GeneratedValue
    private UUID id;

    @Column(
            name = "car_registration_number",
            nullable = false,
            columnDefinition = "varchar(100)"
    )
    private String carRegistrationNumber;

    @Column(
            name = "tenant_phone_number",
            nullable = false,
            columnDefinition = "varchar(15)"
    )
    private String tenantPhoneNumber;

    @Column(
            name = "rent_date",
            nullable = false
    )
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime rentDate;

    @Column(
            name = "return_date",
            nullable = false
    )
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime returnDate;

    @Column(
            name = "final_price",
            nullable = false,
            columnDefinition = "decimal"
    )
    private BigDecimal finalPrice;

    @Version
    private Long version;
}
