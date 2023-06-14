package com.esp.irt.backend.services;

import java.time.LocalDate;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

public class Datagenerated {
    private double precipitation;
    private double waterLevel;
    private int topography;
    private int riverCapacity;
    private int soilType;
    private String ville;
    private LocalDate date;
   
    private int isFlooded;

    public Datagenerated(double precipitation, double waterLevel, int topography, int riverCapacity, int soilType,
                         LocalDate date,String ville, int isFlooded) {
        this.precipitation = precipitation;
        this.waterLevel = waterLevel;
        this.topography = topography;
        this.riverCapacity = riverCapacity;
        this.soilType = soilType;
        this.date = date;
        this.ville=ville;
        this.isFlooded = isFlooded;
    }

    public Datagenerated() {
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(double precipitation) {
        this.precipitation = precipitation;
    }

    public double getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(double waterLevel) {
        this.waterLevel = waterLevel;
    }
    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public int getTopography() {
        return topography;
    }

    public void setTopography(int topography) {
        this.topography = topography;
    }

    public int getRiverCapacity() {
        return riverCapacity;
    }

    public void setRiverCapacity(int riverCapacity) {
        this.riverCapacity = riverCapacity;
    }

    public int getSoilType() {
        return soilType;
    }

    public void setSoilType(int soilType) {
        this.soilType = soilType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getIsFlooded() {
        return isFlooded;
    }

    public void setIsFlooded(int isFlooded) {
        this.isFlooded = isFlooded;
    }

    @Override
    public String toString() {
        return "DataGenerated{" +
                "precipitation=" + precipitation +
                ", waterLevel=" + waterLevel +
                ", topography=" + topography +
                ", riverCapacity=" + riverCapacity +
                ", soilType=" + soilType +
                ", date='" + date + '\'' +
                ", ville=" + ville +
                ", isFlooded=" + isFlooded +
                '}';
    }
}
