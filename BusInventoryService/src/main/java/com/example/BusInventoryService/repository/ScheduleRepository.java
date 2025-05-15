package com.example.BusInventoryService.repository;


import com.example.BusInventoryService.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    @Query("SELECT s FROM Schedule s WHERE s.route.routeId = :routeId " +
            "AND s.departureTime >= :start AND s.departureTime < :end " +
            "AND s.availableSeats > 0")
    List<Schedule> findAvailableSchedules(String routeId, LocalDateTime start, LocalDateTime end);
}