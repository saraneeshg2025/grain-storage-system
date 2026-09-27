package com.example.grainstorage.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SiloTelemetryRequest {

    @NotNull(message = "Warehouse ID is required")
    private Long warehouseId;

    @NotBlank(message = "Silo section is required (e.g. Core-A, Upper Plenum, Hopper)")
    private String siloSection;

    @NotNull(message = "Temperature is required")
    @DecimalMin(value = "-20.0", message = "Temperature out of realistic bounds")
    @DecimalMax(value = "80.0", message = "Temperature out of realistic bounds")
    private Double temperatureCelsius;

    @NotNull(message = "Relative humidity is required")
    @DecimalMin(value = "0.0", message = "Humidity cannot be negative")
    @DecimalMax(value = "100.0", message = "Humidity cannot exceed 100%")
    private Double relativeHumidityPercentage;

    @NotNull(message = "CO2 PPM is required")
    @DecimalMin(value = "200.0", message = "CO2 reading must be at least ambient levels")
    private Double co2Ppm;

    public SiloTelemetryRequest() {
    }

    public SiloTelemetryRequest(Long warehouseId, String siloSection, Double temperatureCelsius, Double relativeHumidityPercentage, Double co2Ppm) {
        this.warehouseId = warehouseId;
        this.siloSection = siloSection;
        this.temperatureCelsius = temperatureCelsius;
        this.relativeHumidityPercentage = relativeHumidityPercentage;
        this.co2Ppm = co2Ppm;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getSiloSection() {
        return siloSection;
    }

    public void setSiloSection(String siloSection) {
        this.siloSection = siloSection;
    }

    public Double getTemperatureCelsius() {
        return temperatureCelsius;
    }

    public void setTemperatureCelsius(Double temperatureCelsius) {
        this.temperatureCelsius = temperatureCelsius;
    }

    public Double getRelativeHumidityPercentage() {
        return relativeHumidityPercentage;
    }

    public void setRelativeHumidityPercentage(Double relativeHumidityPercentage) {
        this.relativeHumidityPercentage = relativeHumidityPercentage;
    }

    public Double getCo2Ppm() {
        return co2Ppm;
    }

    public void setCo2Ppm(Double co2Ppm) {
        this.co2Ppm = co2Ppm;
    }
}
