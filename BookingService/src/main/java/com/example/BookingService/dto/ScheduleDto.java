package com.example.BookingService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDto {

    private Integer routeId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String driverName;
    private int availableSeats;
}
