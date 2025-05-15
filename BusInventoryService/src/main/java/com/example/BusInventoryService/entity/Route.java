package com.example.BusInventoryService.entity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;

@Entity
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Route {

    @Id
    @SequenceGenerator(name = "route_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "route_sequence")
    private Integer routeId;
    private String startCity;
    private String endCity;
    private String distance;
    private String duration;

    @OneToMany(cascade = CascadeType.ALL)
    private List<City> city;
}
