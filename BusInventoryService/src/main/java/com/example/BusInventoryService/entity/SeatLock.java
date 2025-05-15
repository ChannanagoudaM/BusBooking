package com.example.BusInventoryService.entity;

import com.example.BusInventoryService.constants.BookingStatus;
import com.example.BusInventoryService.dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SeatLock {

    @Id
    @SequenceGenerator(name = "seatSequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seatSequence")
    private Integer id;
    @OneToOne
    private Schedule scheduleId;
    private Integer userId;
    private String seatNumber;
    private LocalDateTime lockTime;
    private LocalDateTime expiresAt;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}
