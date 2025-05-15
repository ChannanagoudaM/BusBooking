package com.example.BusInventoryService.repository;

import com.example.BusInventoryService.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, Integer> {
}