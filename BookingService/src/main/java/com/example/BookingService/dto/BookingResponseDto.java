package com.example.BookingService.dto;

import com.example.BookingService.constants.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDto {

    private double amountPaid;
    private PaymentStatus paymentStatus;
    private String contactDetails;
    private String pnrNumber;
    private String bookingId;
    private UserDto userDto;
    private BusDto busDto;
    private ScheduleDto scheduleDto;
    private SeatLockDto seatLockDto;
    private RouteDto routeDto;
    private CityDto cityDto;
}
