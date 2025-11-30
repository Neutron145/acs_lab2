package com.example.lab2.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "drone")
public class Drone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=100)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "controller_id")
    @JsonBackReference
    private FlightController controller;

    @Column(precision = 7, scale = 2)
    private BigDecimal weight;

    public Drone() {}

    public Drone(String type, FlightController controller, BigDecimal weight) {
        this.type = type;
        this.controller = controller;
        this.weight = weight;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public FlightController getController() { return controller; }
    public void setController(FlightController controller) { this.controller = controller; }

    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }
}
