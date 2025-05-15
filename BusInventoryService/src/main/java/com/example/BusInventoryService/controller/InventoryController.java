package com.example.BusInventoryService.controller;

import com.example.BusInventoryService.Response.ApiResponse;
import com.example.BusInventoryService.dto.*;
import com.example.BusInventoryService.entity.*;
import com.example.BusInventoryService.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/buses")
public class InventoryController {

    @Autowired
    private InventoryService service;


    @PostMapping("/addBus")
    public ResponseEntity<String> addBus(@RequestBody Bus bus)
    {
        service.addBus(bus);
        return ResponseEntity.ok("new bus added");
    }

    @GetMapping("/totalSeats")
    public ResponseEntity<Integer> getTotalSeats(@RequestParam String registrationNumber)
    {
        return ResponseEntity.ok(service.totalSeats(registrationNumber));
    }

    @PostMapping("/addCity")
    public ResponseEntity<ApiResponse<City>> addCity(@RequestBody City city)
    {
        ApiResponse<City> apiResponse=new ApiResponse<City>(
                HttpStatus.CREATED,
                "City has been added to database",
                service.addCity(city)
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
    @GetMapping("/getCity")
    public ResponseEntity<ApiResponse<City>> getCity(@RequestParam String cityName)
    {
        ApiResponse<City> apiResponse=new ApiResponse<>(
                HttpStatus.FOUND,
                "City fetched successfully",
                service.fetchCity(cityName)
        );
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("/addRoute")
    public ResponseEntity<ApiResponse<Route>> addRoute(@RequestBody RouteRequestDto routeRequestDto) throws IOException, InterruptedException {
        ApiResponse<Route> apiResponse=new ApiResponse<>(
                HttpStatus.CREATED,
                "route object created successfully",
                service.addRoute(routeRequestDto)
        );
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("/addStop")
    public ResponseEntity<ApiResponse<Stops>> addStops(@RequestBody StopRequestDto stops)
    {
        ApiResponse<Stops> apiResponse=new ApiResponse<>(
                HttpStatus.CREATED,
                "Stops city has been added to db",
                service.addStops(stops)
        );
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("/addSchedule")
    public ResponseEntity<ApiResponse<Schedule>> addSchedule(@RequestBody ScheduleRequestDto schedule)
    {
        ApiResponse<Schedule> apiResponse=new ApiResponse<>(
                HttpStatus.CREATED,
                "Schedule obbject added successfully to database",
                service.addSchedule(schedule)
        );
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @GetMapping("/getSchedule/{id}")
    public Schedule findSchedule(@PathVariable int id)
    {
        return service.getSchedule(id);
    }

    @PostMapping("/addSeatLock")
    public ResponseEntity<ApiResponse<SeatLockResponseDto>> addSeatLock(@RequestBody SeatLockRequestDto seatLockDto, @RequestHeader("Authorization") String token)
    {
        ApiResponse<SeatLockResponseDto> apiResponse=new ApiResponse<>(
                HttpStatus.CREATED,
                "SeatLock added to database",
                service.addSeatLock(seatLockDto,token)
        );
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}