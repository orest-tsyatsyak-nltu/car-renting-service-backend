package com.example.carrentingservicebackend.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class AddRentDTO {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @NotBlank
    private String carRegistrationNumber;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @NotBlank
    @Length(max = 15)
    private String tenantPhoneNumber;

    @NotNull
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema(implementation = String.class, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime rentDate;

    @NotNull
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema(implementation = String.class, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime returnDate;

}
