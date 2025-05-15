package com.example.BusInventoryService.repository;


import com.example.BusInventoryService.entity.Bus;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BusRepository extends JpaRepository<Bus, Integer> {

    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))// it enables query level cache
    @Query("FROM Bus WHERE registrationNumber = :reg")
    Optional<Bus> findByRegistrationNumber(@Param("reg") String registrationNumber);

    //@RequestParam : Extract query parameters from http requests used in controller method
    //@Param : Binds method parameters to named parameters in @Query
}