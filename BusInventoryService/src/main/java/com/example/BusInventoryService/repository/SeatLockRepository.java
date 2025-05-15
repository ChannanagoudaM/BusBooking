package com.example.BusInventoryService.repository;

import com.example.BusInventoryService.entity.SeatLock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatLockRepository extends JpaRepository<SeatLock,Integer> {
}
