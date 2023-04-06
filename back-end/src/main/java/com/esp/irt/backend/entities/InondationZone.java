package com.esp.irt.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import jakarta.persistence.*;

@Entity @NoArgsConstructor @AllArgsConstructor @Data
public class InondationZone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double precipitation;
    private double waterLevel;
    private String topography;
    private int riverCapacity;
    private String soilType;
    private Date date;
    private String ville;
    private double flooded;

}