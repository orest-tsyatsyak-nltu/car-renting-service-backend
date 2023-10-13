package com.example.carrentingservicebackend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "RentRecordEntity")
@Table(name = "rent_records")
@Getter
@Setter
@ToString
@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RentRecordEntity {

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
            name = "return_date",
            nullable = false
    )
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime returnDate;

    @Column(
            name = "delay_id_days",
            nullable = false
    )
    private Integer delayInDays;

    @Column(
            name = "total_payment",
            nullable = false,
            columnDefinition = "decimal"
    )
    private BigDecimal totalPayment;

    @Version
    private Long version;
}
