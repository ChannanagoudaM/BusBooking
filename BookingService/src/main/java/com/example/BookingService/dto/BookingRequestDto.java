package com.example.BookingService.dto;

import com.example.BookingService.constants.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDto {

    private Integer userId;
    private double amountPaid;
    private PaymentStatus paymentStatus;
    private String contactDetails;
    private Integer busId;
    private Integer scheduleId;
    private Integer seatLockId;
    private Integer routeId;
    private Integer cityId;
}
