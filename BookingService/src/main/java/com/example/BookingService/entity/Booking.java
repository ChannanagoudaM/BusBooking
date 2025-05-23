package com.example.BookingService.entity;

import com.example.BookingService.constants.PaymentStatus;
import com.example.BookingService.dto.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @SequenceGenerator(name = "booking_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_sequence")
    private Integer id;
    private String bookingId;
    private double amountPaid;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private String pnr;
    private String contactDetails;

    private Integer userId;

    private Integer scheduleDto;

    private Integer seatLockDto;

    private Integer routeDto;

    private Integer cityDto;

    private Integer busDto;
}
