package com.example.lab2.service;

import com.example.lab2.model.FlightController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface FlightControllerService {
    List<FlightController> getAll();
    Optional<FlightController> getById(long id);

    FlightController create(String name, String manufacturer, BigDecimal cost);
    FlightController update(long id, String name, String manufacturer, BigDecimal cost);
    void delete(long id);
}
