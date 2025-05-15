package com.example.BusInventoryService.service;


import com.example.BusInventoryService.dto.*;
import com.example.BusInventoryService.entity.*;
import com.example.BusInventoryService.exception.BusNotFoundException;
import com.example.BusInventoryService.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@Slf4j
public class InventoryService {

    @Value("${ors.api-key}")
    private String apiKey;

    Logger logger= LoggerFactory.getLogger(InventoryService.class);

    @Autowired
    private final WebClient.Builder webClient;
    RestTemplate restTemplate = new RestTemplate();

    private final BusRepository busRepository;
    private final RouteRepository routeRepository;
    private final ScheduleRepository scheduleRepository;
    private final CityRepository cityRepository;
    private final StopsRepository stopsRepository;
    private final SeatLockRepository seatLockRepository;
    private final ModelMapper modelMapper;

    public InventoryService(WebClient.Builder webClient, BusRepository busRepository, RouteRepository routeRepository, ScheduleRepository scheduleRepository, CityRepository cityRepository, StopsRepository stopsRepository, SeatLockRepository seatLockRepository, ModelMapper modelMapper) {
        this.webClient = webClient;
        this.busRepository = busRepository;
        this.routeRepository = routeRepository;
        this.scheduleRepository = scheduleRepository;
        this.cityRepository = cityRepository;
        this.stopsRepository = stopsRepository;
        this.seatLockRepository = seatLockRepository;
        this.modelMapper = modelMapper;
    }

    //BUS
    public void addBus(Bus bus) {
        busRepository.save(bus);
    }

    public int totalSeats(String registrationNumber) {
        Bus bus = busRepository.findByRegistrationNumber(registrationNumber).orElseThrow(() -> new BusNotFoundException("Bus not found"));
        return bus.getTotalSeats();
    }

    //CITY

    public City addCity(City city) {
        return cityRepository.save(city);
    }

    public City fetchCity(String name) {
        return cityRepository.findByName(name);
    }

    //STOPS
    public Stops addStops(StopRequestDto stopRequestDto) {
        System.out.println(stopRequestDto.getCityId() + " " + stopRequestDto.getRouteId());
        City city = cityRepository.findById(stopRequestDto.getCityId()).get();
        logger.info("City {}",city);
        Route route = routeRepository.findById(stopRequestDto.getRouteId()).get();
        logger.info("Route {}", route);
        Stops stops = new Stops();
        stops.setRoute(route);
        stops.setCity(city);
        stops.setArrivalTime(stopRequestDto.getArrivalTime());
        stops.setDepartureTime(stopRequestDto.getDepartureTime());
        stops.setStopOrder(stopRequestDto.getStopOrder());
        return stopsRepository.save(stops);
    }

    //ROUTES
    public Route addRoute(RouteRequestDto routeRequestDto) throws IOException, InterruptedException {
        DistanceResponse distanceBetweenCities = getDistanceBetweenCities(routeRequestDto.getStartCity(), routeRequestDto.getEndCity());
        Route route = new Route();
        route.setStartCity(routeRequestDto.getStartCity());
        route.setEndCity(routeRequestDto.getEndCity());
        route.setCity(routeRequestDto.getCity());
        route.setDuration(distanceBetweenCities.getTravelTime());
        route.setDistance(distanceBetweenCities.getDistanceInKilometers());
        return routeRepository.save(route);
    }

    //SCHEDULE
    public Schedule addSchedule(ScheduleRequestDto scheduleRequestDto) {
        Route route = routeRepository.findById(scheduleRequestDto.getRouteId()).get();
        Bus bus = busRepository.findById(scheduleRequestDto.getBusId()).get();

        Schedule schedule = new Schedule();
        schedule.setBus(bus);
        schedule.setRoute(route);
        schedule.setArrivalTime(scheduleRequestDto.getArrivalTime());
        schedule.setDepartureTime(scheduleRequestDto.getDepartureTime());
        schedule.setDriverName(scheduleRequestDto.getDriverName());
        schedule.setAvailableSeats(scheduleRequestDto.getAvailableSeats());
        return scheduleRepository.save(schedule);
    }

    public Schedule getSchedule(int id)
    {
        return scheduleRepository.findById(id).get();
    }

    //SEAT LOCK
    public SeatLockResponseDto addSeatLock(SeatLockRequestDto seatLockDto,String token) {
        Schedule schedule = scheduleRepository.findById(seatLockDto.getScheduleId()).get();
        logger.info("Schedule {}", schedule);
        WebClient build = webClient.baseUrl("http://localhost:8090/users/").build();
        UserDto userDto = build.get().uri("getById/{id}", seatLockDto.getUserId())
                .header("Authorization",token)
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
        logger.info("UserDto {}", userDto);
        SeatLock seatLock = new SeatLock();
        seatLock.setUserId(userDto.getId());
        seatLock.setScheduleId(schedule);
        LocalDateTime now = LocalDateTime.now();
        seatLock.setExpiresAt(now.plusMinutes(10));
        seatLock.setLockTime(now);
        seatLock.setSeatNumber(seatLockDto.getSeatNumber());
        seatLock.setStatus(seatLockDto.getStatus());

        SeatLockResponseDto map = modelMapper.map(seatLock, SeatLockResponseDto.class);
        map.setUserId(userDto);
        logger.info("SeatLockResponseDto {}",map);
        seatLockRepository.save(seatLock);
        return map;
    }

    public DistanceResponse getDistanceBetweenCities(String origin, String destination) throws IOException, InterruptedException {

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", "2c05273a73msh8a43a986d299865p1a38a6jsn9f69709ac137");
        headers.set("x-rapidapi-host", "driving-distance-calculator-between-two-points.p.rapidapi.com");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = String.format("https://driving-distance-calculator-between-two-points.p.rapidapi.com/data?origin=%s&destination=%s", origin, destination);
        ResponseEntity<String> response1 = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        logger.info("Distance response {}",response1);
        return objectMapper.readValue(response1.getBody(), DistanceResponse.class);
    }
}
