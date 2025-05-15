package com.example.BusInventoryService.entity;

import com.example.BusInventoryService.constants.BusType;
import com.example.BusInventoryService.constants.CustomConverter;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;

@Entity
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(indexes = {@Index(name = "idx_bus_registrationNumber", columnList = "registrationNumber")})
public class Bus {

    @Id
    @SequenceGenerator(name = "bus_sequence",sequenceName = "bus_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bus_sequence")
    private Integer busId;
    private String travelsName;
    @Convert(converter = CustomConverter.class)
    private List<BusType> type;

    private int totalSeats;

    @Column(unique = true)
    private String registrationNumber;
}