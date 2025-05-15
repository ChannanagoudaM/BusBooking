package com.example.BusInventoryService.dto;

import com.example.BusInventoryService.constants.BookingStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatLockRequestDto {

    private Integer scheduleId;
    private String seatNumber;
    private Integer userId;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}
