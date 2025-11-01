package com.example.lab2.repository;

import com.example.lab2.model.Drone;
import com.example.lab2.model.FlightController;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, Long> {
    List<Drone> findByType(String type);
    List<Drone> findByController(FlightController controller);
}
