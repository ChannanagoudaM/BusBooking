package com.example.BookingService.controller;

import com.example.BookingService.configuration.AsyncConfig;
import com.example.BookingService.dto.BookingRequestDto;
import com.example.BookingService.dto.BookingResponseDto;
import com.example.BookingService.dto.UserDto;
import com.example.BookingService.service.BookingService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;
    private final Executor asyncConfig;

    public BookingController(BookingService bookingService,@Qualifier("taskExecutor") Executor asyncConfig)
    {
        this.bookingService=bookingService;
        this.asyncConfig = asyncConfig;
    }

    @GetMapping("/get-user")
    public ResponseEntity<UserDto> getUser(@RequestParam String userId) {
        UserDto user = bookingService.fetchUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/send/{id}")
    public ResponseEntity<String> send(@PathVariable int id)
    {
        bookingService.sendUserIdRequest(id);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/addBooking")
    public ResponseEntity<CompletableFuture<BookingResponseDto>> addBooking(
            @RequestBody BookingRequestDto bookingRequestDto,
            @RequestHeader("Authorization") String token) throws ExecutionException, InterruptedException {
        CompletableFuture<BookingResponseDto> bookingResponseDtoCompletableFuture = bookingService.addBooking(bookingRequestDto, token);
        bookingResponseDtoCompletableFuture.thenAccept(System.out::println);
        return ResponseEntity.ok(bookingResponseDtoCompletableFuture);
    }

    @GetMapping("/practice")
    public ResponseEntity<String> practise()
    {
        return ResponseEntity.ok("Ok");
    }
}
