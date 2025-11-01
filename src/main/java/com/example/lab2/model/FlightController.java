package com.example.lab2.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "flight_controller")
public class FlightController {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100)
    private  String manufacturer;

    @Column(precision = 10, scale = 2)
    private BigDecimal cost;

    @OneToMany(mappedBy = "controller", fetch = FetchType.LAZY)
    private  List<Drone> drones = new ArrayList<>();

    public  FlightController() {}

    public FlightController(String name, String manufacturer, BigDecimal cost) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.cost = cost;
    }

    public Long getId() { return id; }
    public void setId(Long id) {this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    public BigDecimal getCost() { return  cost; }
    public void setCost(BigDecimal cost) { this.cost = cost; }

    public List<Drone> getDrones() { return drones; }
    public void setDrones(List<Drone> drones) { this.drones = drones; }

}
