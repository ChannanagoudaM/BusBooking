package com.example.BusInventoryService.repository;

import com.example.BusInventoryService.entity.Stops;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopsRepository extends JpaRepository<Stops,Integer> {
}
