package com.example.BookingService.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusDto {

    private Integer busId;
    private String travelsName;
    private List<String> type;
    private String registrationNumber;
    private int totalSeats;
}
