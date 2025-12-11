package com.example.lab2.rest;

import com.example.lab2.model.ChangeLog;
import com.example.lab2.repository.ChangeLogRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/change-log")
public class ChangeLogRestController {

    private final ChangeLogRepository changeLogRepository;

    public ChangeLogRestController(ChangeLogRepository changeLogRepository) {
        this.changeLogRepository = changeLogRepository;
    }

    // при желании можно иметь и JSON-версию
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<ChangeLog> getAll() {
        return changeLogRepository.findAll();
    }

    @GetMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getAllAsXml() {
        List<ChangeLog> logs = changeLogRepository.findAll();

        StringBuilder sb = new StringBuilder();

        // заголовок XML + привязка XSL
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<?xml-stylesheet type=\"text/xsl\" href=\"/xsl/changeLog.xsl\"?>\n");
        sb.append("<changeLogs>\n");

        for (ChangeLog log : logs) {
            sb.append("  <changeLog>\n");
            sb.append("    <id>").append(log.getEntityId()).append("</id>\n");
            sb.append("    <entityName>")
                    .append(escapeXml(log.getEntityName()))
                    .append("</entityName>\n");
            sb.append("    <entityId>").append(log.getEntityId()).append("</entityId>\n");
            sb.append("    <operation>")
                    .append(escapeXml(log.getOperation()))
                    .append("</operation>\n");
            sb.append("    <changeTime>").append(log.getChangeTime()).append("</changeTime>\n");
            sb.append("    <details>")
                    .append(escapeXml(log.getDetails()))
                    .append("</details>\n");
            sb.append("  </changeLog>\n");
        }

        sb.append("</changeLogs>\n");

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
