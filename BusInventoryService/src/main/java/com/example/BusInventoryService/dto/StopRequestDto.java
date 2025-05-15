package com.example.BusInventoryService.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StopRequestDto {


    private Integer routeId;
    private Integer cityId;
    private int stopOrder;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
}
