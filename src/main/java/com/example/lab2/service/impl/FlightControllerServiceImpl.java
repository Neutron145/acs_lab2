package com.example.lab2.service.impl;

import com.example.lab2.model.FlightController;
import com.example.lab2.repository.FlightControllerRepository;
import com.example.lab2.service.FlightControllerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FlightControllerServiceImpl implements FlightControllerService {

    private final FlightControllerRepository repo;

    public FlightControllerServiceImpl(FlightControllerRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<FlightController> getAll() {
        return repo.findAll();
    }

    @Override
    public Optional<FlightController> getById(long id) {
        return repo.findById(id);
    }

    @Override
    @Transactional
    public FlightController create(String name, String manufacturer, BigDecimal cost) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }
        if (repo.existsByName(name)) {
            throw new IllegalStateException("FlightController with same name already exists");
        }
        FlightController fc = new FlightController();
        fc.setName(name.trim());
        fc.setManufacturer(manufacturer);
        fc.setCost(cost);
        return repo.save(fc);
    }

    @Override
    @Transactional
    public FlightController update(long id, String name, String manufacturer, BigDecimal cost) {
        FlightController fc = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("FlightController not found: id=" + id));
        if (name != null && !name.isBlank()) fc.setName(name.trim());
        fc.setManufacturer(manufacturer);
        fc.setCost(cost);
        return repo.save(fc);
    }

    @Override
    @Transactional
    public void delete(long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("FlightController not found: id=" + id);
        }
        repo.deleteById(id);
    }
}