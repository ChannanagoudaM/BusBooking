package com.example.BookingService.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatLockDto {

    private Integer userId;
    private String seatNumber;
    private LocalDateTime lockTime;
    private LocalDateTime expiresAt;
}
