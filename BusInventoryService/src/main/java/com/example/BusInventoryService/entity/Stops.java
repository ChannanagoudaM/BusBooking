package com.example.BusInventoryService.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Stops {

    @Id
    @SequenceGenerator(name = "stops_sequence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stops_sequence")
    private Integer id;
    @ManyToOne
    private Route route;
    @ManyToOne
    private City city;
    private int stopOrder;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;

}
