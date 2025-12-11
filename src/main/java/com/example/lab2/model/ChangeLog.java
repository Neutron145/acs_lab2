package com.example.lab2.model;

import jakarta.persistence.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "change_log")
public class ChangeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entityName;
    private Long entityId;
    private String operation;
    private LocalDateTime changeTime;

    @Column(columnDefinition = "text")
    private String details;

    public void setEntityName(String entityName) { this.entityName = entityName; }
    public String getEntityName() { return this.entityName; }
    public void setEntityId(Long entityId) { this.entityId = entityId; }
    public Long getEntityId() { return this.entityId; }
    public void setOperation(String operation) { this.operation = operation; }
    public String getOperation() { return this.operation; }
    public void setChangeTime(LocalDateTime changeTime) { this.changeTime = changeTime; }
    public LocalDateTime getChangeTime() { return this.changeTime; }
    public void setDetails(String details) { this.details = details; }
    public String getDetails() { return this.details; }

}
