package com.example.lab2.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ChangeEvent implements Serializable {
    private String entityName;
    private Long entityId;
    private ChangeOperation operation;
    private LocalDateTime changeTime;
    private String details;
    private Double cost;

    public ChangeEvent() {
    }

    public ChangeEvent(String entityName,
                       Long entityId,
                       ChangeOperation operation,
                       LocalDateTime changeTime,
                       String details,
                       Double cost) {
        this.entityName = entityName;
        this.entityId = entityId;
        this.operation = operation;
        this.changeTime = changeTime;
        this.details = details;
        this.cost = cost;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public ChangeOperation getOperation() {
        return operation;
    }

    public void setOperation(ChangeOperation operation) {
        this.operation = operation;
    }

    public LocalDateTime getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(LocalDateTime changeTime) {
        this.changeTime = changeTime;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
