package com.example.BookingService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteDto {

    private String startCity;
    private String endCity;
    private String distance;
    private String duration;
    private List<CityDto> city;
}
