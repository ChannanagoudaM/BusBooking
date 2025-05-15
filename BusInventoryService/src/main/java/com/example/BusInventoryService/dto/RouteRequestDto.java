package com.example.BusInventoryService.dto;

import com.example.BusInventoryService.entity.City;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteRequestDto {


    private String startCity;
    private String endCity;

    @OneToMany(cascade = CascadeType.ALL)
    private List<City> city;
}
