package com.example.carrentingservicebackend.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class GetRentRecordDTO extends RepresentationModel<GetRentRecordDTO> {

    private UUID id;

    private String carRegistrationNumber;

    private String tenantPhoneNumber;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema(implementation = String.class, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime returnDate;

    private Integer delayInDays;

    private BigDecimal totalPayment;
}
