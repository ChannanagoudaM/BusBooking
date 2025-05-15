package com.example.BusInventoryService.dto;

import com.example.BusInventoryService.constants.BookingStatus;
import com.example.BusInventoryService.entity.Schedule;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatLockResponseDto {

    private Schedule scheduleId;
    private UserDto userId;
    private String seatNumber;
    private LocalDateTime lockTime;
    private LocalDateTime expiresAt;
    private BookingStatus status;
}
