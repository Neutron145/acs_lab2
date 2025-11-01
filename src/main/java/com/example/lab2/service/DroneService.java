package com.example.lab2.service;

import com.example.lab2.model.Drone;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface DroneService {
    List<Drone> getAll();
    Optional<Drone> getById(long id);

    Drone create(String type, Long controllerId, BigDecimal weight);
    Drone update(long id, String type, Long controllerId, BigDecimal weight);
    void delete(long id);

    List<Drone> findByType(String type);
}
