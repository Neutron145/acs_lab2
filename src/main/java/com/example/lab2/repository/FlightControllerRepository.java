package com.example.lab2.repository;

import com.example.lab2.model.FlightController;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightControllerRepository extends JpaRepository<FlightController, Long> {
    boolean existsByName(String name);
}