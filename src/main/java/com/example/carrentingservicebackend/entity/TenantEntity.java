package com.example.carrentingservicebackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

import java.util.UUID;

@Entity(name = "Tenant")
@Table(
        name = "tenants",
        uniqueConstraints = @UniqueConstraint(
                name = "tenants_phone_number_key",
                columnNames = "phone_number"
        )
)
@Getter
@Setter
@ToString
@Builder(setterPrefix = "with")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TenantEntity {
    @Id
    @Column(name = "id", nullable = false, columnDefinition="binary(16) not null")
    @GeneratedValue
    private UUID id;

    @Column(
            name = "fullname",
            nullable = false,
            columnDefinition = "varchar(100)"
    )
    private String fullName;

    @Column(
            name = "address",
            nullable = false,
            columnDefinition = "varchar(100)"
    )
    private String address;

    @Column(
            name = "phone_number",
            nullable = false,
            columnDefinition = "varchar(100)"
    )
    private String phoneNumber;

    @Version
    private Long version;
}
