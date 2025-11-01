package com.example.lab2.service.impl;

import com.example.lab2.model.Drone;
import com.example.lab2.model.FlightController;
import com.example.lab2.repository.DroneRepository;
import com.example.lab2.repository.FlightControllerRepository;
import com.example.lab2.service.DroneService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DroneServiceImpl implements DroneService {

    private final DroneRepository droneRepo;
    private final FlightControllerRepository fcRepo;

    public DroneServiceImpl(DroneRepository droneRepo, FlightControllerRepository fcRepo) {
        this.droneRepo = droneRepo;
        this.fcRepo = fcRepo;
    }

    @Override
    public List<Drone> getAll() {
        return droneRepo.findAll();
    }

    @Override
    public Optional<Drone> getById(long id) {
        return droneRepo.findById(id);
    }

    @Override
    @Transactional
    public Drone create(String type, Long controllerId, BigDecimal weight) {
        Drone d = new Drone();
        d.setType(type);
        d.setWeight(weight);

        if (controllerId != null) {
            FlightController fc = fcRepo.findById(controllerId)
                    .orElseThrow(() -> new IllegalArgumentException("FlightController not found: id=" + controllerId));
            d.setController(fc);
        }
        return droneRepo.save(d);
    }

    @Override
    @Transactional
    public Drone update(long id, String type, Long controllerId, BigDecimal weight) {
        Drone d = droneRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Drone not found: id=" + id));
        if (type != null && !type.isBlank()) d.setType(type);
        d.setWeight(weight);

        if (controllerId != null) {
            FlightController fc = fcRepo.findById(controllerId)
                    .orElseThrow(() -> new IllegalArgumentException("FlightController not found: id=" + controllerId));
            d.setController(fc);
        } else {
            d.setController(null);
        }
        return droneRepo.save(d);
    }

    @Override
    @Transactional
    public void delete(long id) {
        if (!droneRepo.existsById(id)) {
            throw new IllegalArgumentException("Drone not found: id=" + id);
        }
        droneRepo.deleteById(id);
    }

    @Override
    public List<Drone> findByType(String type) {
        return droneRepo.findByType(type);
    }
}
