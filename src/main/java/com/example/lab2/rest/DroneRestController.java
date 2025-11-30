package com.example.lab2.rest;

import com.example.lab2.model.Drone;
import com.example.lab2.model.FlightController;
import com.example.lab2.repository.DroneRepository;
import com.example.lab2.repository.FlightControllerRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/drones")
public class DroneRestController {
    private final DroneRepository droneRepository;
    private final FlightControllerRepository flightControllerRepository;


    public DroneRestController(DroneRepository droneRepository,
                               FlightControllerRepository flightControllerRepository) {
        this.droneRepository = droneRepository;
        this.flightControllerRepository = flightControllerRepository;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<Drone> getAll() {
        return droneRepository.findAll();
    }

    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Drone> getById(@PathVariable Long id) {
        return droneRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Drone> create(@RequestBody Drone drone) {
        if (drone.getController() != null &&
                drone.getController().getId() != null) {
            FlightController fc = flightControllerRepository
                    .findById(drone.getController().getId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Invalid Controller id"));
            drone.setController(fc);
        }

        Drone saved = droneRepository.save(drone);
        URI location = URI.create("/api/drones/" + saved.getId());
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping(
            value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Drone> update(@PathVariable Long id, @RequestBody Drone drone) {
        if (!droneRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        drone.setId(id);

        if (drone.getController() != null &&
                drone.getController().getId() != null) {
            FlightController fc = flightControllerRepository
                    .findById(drone.getController().getId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Invalid flightController id"));
            drone.setController(fc);
        }

        Drone saved = droneRepository.save(drone);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!droneRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        droneRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private String escape(String s) {
        if (s == null) return "";
        return s
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }

    @GetMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getAllAsXml() {
        var drones = droneRepository.findAll();

        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<?xml-stylesheet type=\"text/xsl\" href=\"/xsl/drones.xsl\"?>\n");
        sb.append("<drones>\n");

        drones.forEach(drone -> {
            sb.append("  <drone>\n");
            sb.append("    <id>").append(drone.getId()).append("</id>\n");
            sb.append("    <type>").append(escapeXml(drone.getType())).append("</type>\n");

            var controller = drone.getController();
            if (controller != null) {
                sb.append("    <controller>\n");
                sb.append("      <id>").append(controller.getId()).append("</id>\n");
                sb.append("      <name>").append(escapeXml(controller.getName())).append("</name>\n");
                sb.append("      <manufacturer>").append(escapeXml(controller.getManufacturer())).append("</manufacturer>\n");
                sb.append("    </controller>\n");
            }

            sb.append("  </drone>\n");
        });

        sb.append("</drones>");

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_XML)
                .body(sb.toString());
    }

    private String escapeXml(String s) {
        if (s == null) return "";
        return s
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}
