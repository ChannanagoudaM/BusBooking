package com.example.BookingService.service;

import com.example.BookingService.constants.RabbitMQConstants;
import com.example.BookingService.dto.*;
import com.example.BookingService.entity.Booking;
import com.example.BookingService.repository.BookingRepository;
import com.example.BookingService.response.ApiResponse;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.http.HttpRequest;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@EnableAsync
public class BookingService {

    private final BookingRepository bookingRepository;
    private final AmqpTemplate amqpTemplate;
    private final RabbitTemplate rabbitTemplate;

    private final WebClient userWebclient;
    private final WebClient busWebclient;

    public BookingService(BookingRepository bookingRepository, AmqpTemplate amqpTemplate, RabbitTemplate rabbitTemplate, WebClient userWebclient, WebClient busWebclient) {
        this.bookingRepository = bookingRepository;
        this.amqpTemplate = amqpTemplate;
        this.rabbitTemplate = rabbitTemplate;
        this.userWebclient = userWebclient;
        this.busWebclient = busWebclient;
    }

    public void sendUserIdRequest(int userId) {
        amqpTemplate.convertAndSend("userExchange", "userKey", userId);
        System.out.println("Sent userId: " + userId);
    }

    @Async("taskExecutor")
    public CompletableFuture<BookingResponseDto> addBooking(BookingRequestDto bookingRequestDto, String token) throws ExecutionException, InterruptedException {
        Booking booking = new Booking();
        booking.setBookingId(generateBookingId());
        booking.setAmountPaid(bookingRequestDto.getAmountPaid());
        booking.setPaymentStatus(bookingRequestDto.getPaymentStatus());
        booking.setContactDetails(bookingRequestDto.getContactDetails());
        booking.setPnr(generatePnr());

        booking.setBusDto(bookingRequestDto.getBusId());
        booking.setScheduleDto(bookingRequestDto.getScheduleId());
        booking.setCityDto(bookingRequestDto.getCityId());
        booking.setRouteDto(bookingRequestDto.getRouteId());
        booking.setUserId(bookingRequestDto.getUserId());
        booking.setSeatLockDto(bookingRequestDto.getSeatLockId());

        // Start all async calls
        CompletableFuture<ScheduleDto> scheduleFuture = busWebclient.get()
                .uri("getSchedule/{id}", bookingRequestDto.getScheduleId())
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(ScheduleDto.class)
                .toFuture();

        CompletableFuture<BusDto> busFuture = busWebclient.get()
                .uri("getBusById/{id}", bookingRequestDto.getBusId())
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(BusDto.class)
                .toFuture();

        CompletableFuture<SeatLockDto> seatLockFuture = busWebclient.get()
                .uri("/getSeatLockById/{id}", bookingRequestDto.getSeatLockId())
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(SeatLockDto.class)
                .toFuture();

        CompletableFuture<RouteDto> routeFuture = busWebclient.get()
                .uri("/getRouteById/{id}", bookingRequestDto.getRouteId())
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(RouteDto.class)
                .toFuture();

        CompletableFuture<CityDto> cityFuture = busWebclient.get()
                .uri("/getCityById/{id}", bookingRequestDto.getCityId())
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(CityDto.class)
                .toFuture();

        CompletableFuture<UserDto> userFuture = userWebclient.get()
                .uri("/getById/{id}", bookingRequestDto.getUserId())
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(UserDto.class)
                .toFuture();

        // Combine all futures
        return CompletableFuture.allOf(scheduleFuture, busFuture, seatLockFuture, routeFuture, cityFuture, userFuture)
                .thenApply(voided -> {
                    BookingResponseDto response = new BookingResponseDto();
                    response.setBookingId(booking.getBookingId());
                    response.setAmountPaid(booking.getAmountPaid());
                    response.setPaymentStatus(booking.getPaymentStatus());
                    response.setContactDetails(booking.getContactDetails());
                    response.setPnrNumber(booking.getPnr());

                    response.setScheduleDto(scheduleFuture.join());
                    response.setBusDto(busFuture.join());
                    response.setSeatLockDto(seatLockFuture.join());
                    response.setRouteDto(routeFuture.join());
                    response.setCityDto(cityFuture.join());
                    response.setUserDto(userFuture.join());
                    return response;
                });
    }

//may-16 Endangered species


    public String generateBookingId() {
        Random random = new Random();
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder bookingId = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            bookingId.append(letters.charAt(random.nextInt(letters.length())));
        }
        for (int i = 0; i < 13; i++) {
            bookingId.append(random.nextInt(10));
        }
        return bookingId.toString();
    }

    public String generatePnr()
    {
        SecureRandom secureRandom=new SecureRandom();
        return "PNR"+100000+secureRandom.nextInt(900000);
    }

    public UserDto fetchUserById(String userId) {
        return (UserDto) rabbitTemplate.convertSendAndReceive(
                RabbitMQConstants.USER_EXCHANGE,
                RabbitMQConstants.USER_ROUTING_KEY,
                userId
        );
    }
}
