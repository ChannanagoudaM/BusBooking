package com.example.BusInventoryService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistanceResponse {

    private String originLocation;
    private String destinationLocation;
    private String distanceInMiles;
    private String distanceInKilometers;
    private String distanceInNauticalMiles;
    private String travelTime;
    private double originLatitude;
    private double originLongitude;
    private double destinationLatitude;
    private double destinationLongitude;
}
