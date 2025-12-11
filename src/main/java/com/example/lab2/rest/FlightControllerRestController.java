package com.example.lab2.rest;

import org.springframework.http.MediaType;
import com.example.lab2.model.FlightController;
import com.example.lab2.repository.FlightControllerRepository;
import com.example.lab2.model.ChangeEvent;
import com.example.lab2.model.ChangeOperation;
import com.example.lab2.service.ChangeEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/flight-controllers")
public class FlightControllerRestController {

    private final FlightControllerRepository flightControllerRepository;
    private final ChangeEventPublisher changeEventPublisher;

    public FlightControllerRestController(FlightControllerRepository flightControllerRepository,
                                          ChangeEventPublisher changeEventPublisher) {
        this.flightControllerRepository = flightControllerRepository;
        this.changeEventPublisher = changeEventPublisher;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<FlightController> getAll() {
        return flightControllerRepository.findAll();
    }

    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<FlightController> getById(@PathVariable Long id) {
        return flightControllerRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<FlightController> create(@RequestBody FlightController fc) {
        fc.setId(null);
        FlightController saved = flightControllerRepository.save(fc);

        ChangeEvent event = new ChangeEvent(
                "FlightController",
                saved.getId(),
                ChangeOperation.INSERT,
                LocalDateTime.now(),
                "Создан контроллер: name=" + saved.getName() +
                        ", manufacturer=" + saved.getManufacturer() +
                        ", cost=" + saved.getCost(),
                saved.getCost() != null ? saved.getCost().doubleValue() : null
        );
        changeEventPublisher.publish(event);

        URI location = URI.create("/api/flight-controllers/" + saved.getId());
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping(
            value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<FlightController> update(@PathVariable Long id,
                                                   @RequestBody FlightController fc) {
        if (!flightControllerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        fc.setId(id);
        FlightController saved = flightControllerRepository.save(fc);

        ChangeEvent event = new ChangeEvent(
                "FlightController",
                saved.getId(),
                ChangeOperation.UPDATE,
                LocalDateTime.now(),
                "Обновлён контроллер: name=" + saved.getName() +
                        ", manufacturer=" + saved.getManufacturer() +
                        ", cost=" + saved.getCost(),
                saved.getCost() != null ? saved.getCost().doubleValue() : null
        );
        changeEventPublisher.publish(event);

        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!flightControllerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        flightControllerRepository.deleteById(id);

        ChangeEvent event = new ChangeEvent(
                "FlightController",
                id,
                ChangeOperation.DELETE,
                LocalDateTime.now(),
                "Удалён контроллер с id=" + id,
                null
        );
        changeEventPublisher.publish(event);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getAllAsXml() {
        var controllers = flightControllerRepository.findAll();

        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<?xml-stylesheet type=\"text/xsl\" href=\"/xsl/flightControllers.xsl\"?>\n");
        sb.append("<flightControllers>\n");

        controllers.forEach(fc -> {
            sb.append("  <flightController>\n");
            sb.append("    <id>").append(fc.getId()).append("</id>\n");
            sb.append("    <name>").append(escapeXml(fc.getName())).append("</name>\n");
            sb.append("    <manufacturer>").append(escapeXml(fc.getManufacturer())).append("</manufacturer>\n");
            sb.append("    <cost>").append(fc.getCost()).append("</cost>\n");
            sb.append("  </flightController>\n");
        });

        sb.append("</flightControllers>");

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
