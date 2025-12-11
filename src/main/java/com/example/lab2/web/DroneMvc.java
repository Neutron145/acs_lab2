package com.example.lab2.web;

import com.example.lab2.model.Drone;
import com.example.lab2.model.FlightController;
import com.example.lab2.model.ChangeEvent;
import com.example.lab2.model.ChangeOperation;
import com.example.lab2.repository.DroneRepository;
import com.example.lab2.repository.FlightControllerRepository;
import com.example.lab2.service.ChangeEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/ui/drones")
public class DroneMvc {

    private final DroneRepository droneRepository;
    private final FlightControllerRepository flightControllerRepository;
    private final ChangeEventPublisher changeEventPublisher;

    public DroneMvc(DroneRepository droneRepository,
                                FlightControllerRepository flightControllerRepository,
                                ChangeEventPublisher changeEventPublisher) {
        this.droneRepository = droneRepository;
        this.flightControllerRepository = flightControllerRepository;
        this.changeEventPublisher = changeEventPublisher;
    }

    @PostMapping("/create")
    public String create(@RequestParam String type,
                         @RequestParam(required = false) Long controllerId) {

        Drone drone = new Drone();
        drone.setType(type);

        if (controllerId != null) {
            flightControllerRepository.findById(controllerId)
                    .ifPresent(drone::setController);
        }

        Drone saved = droneRepository.save(drone);

        ChangeEvent event = new ChangeEvent(
                "Drone",
                saved.getId(),
                ChangeOperation.INSERT,
                LocalDateTime.now(),
                "Создан дрон: type=" + saved.getType(),
                null          // cost для дронов не используется
        );
        changeEventPublisher.publish(event);

        return "redirect:/api/drones/xml";
    }

    @PostMapping("/update")
    public String update(@RequestParam Long id,
                         @RequestParam String type,
                         @RequestParam(required = false) Long controllerId) {

        Drone drone = droneRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Drone not found: " + id));

        drone.setType(type);

        if (controllerId != null) {
            FlightController fc = flightControllerRepository.findById(controllerId)
                    .orElse(null);
            drone.setController(fc);
        } else {
            drone.setController(null);
        }

        Drone saved = droneRepository.save(drone);

        ChangeEvent event = new ChangeEvent(
                "Drone",
                saved.getId(),
                ChangeOperation.UPDATE,
                LocalDateTime.now(),
                "Обновлён дрон: type=" + saved.getType(),
                null
        );
        changeEventPublisher.publish(event);

        return "redirect:/api/drones/xml";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id) {

        if (droneRepository.existsById(id)) {
            droneRepository.deleteById(id);

            ChangeEvent event = new ChangeEvent(
                    "Drone",
                    id,
                    ChangeOperation.DELETE,
                    LocalDateTime.now(),
                    "Удалён дрон с id=" + id,
                    null
            );
            changeEventPublisher.publish(event);
        }

        return "redirect:/api/drones/xml";
    }
}