package com.example.lab2.web;

import com.example.lab2.model.FlightController;
import com.example.lab2.model.ChangeEvent;
import com.example.lab2.model.ChangeOperation;
import com.example.lab2.repository.FlightControllerRepository;
import com.example.lab2.service.ChangeEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/ui/flight-controllers")
public class FlightControllerMvc {

    private final FlightControllerRepository flightControllerRepository;
    private final ChangeEventPublisher changeEventPublisher;

    public FlightControllerMvc(FlightControllerRepository flightControllerRepository,
                               ChangeEventPublisher changeEventPublisher) {
        this.flightControllerRepository = flightControllerRepository;
        this.changeEventPublisher = changeEventPublisher;
    }

    @PostMapping("/create")
    public String create(@RequestParam String name,
                         @RequestParam(required = false) String manufacturer,
                         @RequestParam BigDecimal cost) {

        FlightController fc = new FlightController();
        fc.setName(name);
        fc.setManufacturer(manufacturer);
        fc.setCost(cost);

        FlightController saved = flightControllerRepository.save(fc);

        ChangeEvent event = new ChangeEvent(
                "FlightController",
                saved.getId(),
                ChangeOperation.INSERT,
                LocalDateTime.now(),
                "Создан контроллер: " + saved.getName(),
                saved.getCost() != null ? saved.getCost().doubleValue() : null
        );
        changeEventPublisher.publish(event);

        return "redirect:/api/flight-controllers/xml";
    }

    @PostMapping("/update")
    public String update(@RequestParam Long id,
                         @RequestParam String name,
                         @RequestParam(required = false) String manufacturer,
                         @RequestParam BigDecimal cost) {

        FlightController fc = flightControllerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("FlightController not found: " + id));

        fc.setName(name);
        fc.setManufacturer(manufacturer);
        fc.setCost(cost);

        FlightController saved = flightControllerRepository.save(fc);

        ChangeEvent event = new ChangeEvent(
                "FlightController",
                saved.getId(),
                ChangeOperation.UPDATE,
                LocalDateTime.now(),
                "Обновлён контроллер: " + saved.getName(),
                saved.getCost() != null ? saved.getCost().doubleValue() : null
        );
        changeEventPublisher.publish(event);

        return "redirect:/api/flight-controllers/xml";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id) {

        if (flightControllerRepository.existsById(id)) {
            flightControllerRepository.deleteById(id);

            ChangeEvent event = new ChangeEvent(
                    "FlightController",
                    id,
                    ChangeOperation.DELETE,
                    LocalDateTime.now(),
                    "Удалён контроллер id=" + id,
                    null
            );
            changeEventPublisher.publish(event);
        }

        return "redirect:/api/flight-controllers/xml";
    }
}
