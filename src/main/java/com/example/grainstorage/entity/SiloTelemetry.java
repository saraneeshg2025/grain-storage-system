package com.example.grainstorage.entity;

import com.example.grainstorage.entity.enums.SpoilageRiskLevel;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "silo_telemetry")
public class SiloTelemetry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Column(name = "silo_section", nullable = false, length = 100)
    private String siloSection;

    @Column(name = "temperature_celsius", nullable = false)
    private Double temperatureCelsius;

    @Column(name = "relative_humidity_percentage", nullable = false)
    private Double relativeHumidityPercentage;

    @Column(name = "co2_ppm", nullable = false)
    private Double co2Ppm;

    @Column(name = "aeration_fan_active", nullable = false)
    private Boolean aerationFanActive = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level", nullable = false, length = 50)
    private SpoilageRiskLevel riskLevel;

    @Column(name = "recommendation", length = 255)
    private String recommendation;

    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;

    public SiloTelemetry() {
    }

    public SiloTelemetry(Warehouse warehouse, String siloSection, Double temperatureCelsius,
                         Double relativeHumidityPercentage, Double co2Ppm) {
        this.warehouse = warehouse;
        this.siloSection = siloSection;
        this.temperatureCelsius = temperatureCelsius;
        this.relativeHumidityPercentage = relativeHumidityPercentage;
        this.co2Ppm = co2Ppm;
        this.recordedAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        if (this.recordedAt == null) {
            this.recordedAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
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

    public Boolean getAerationFanActive() {
        return aerationFanActive;
    }

    public void setAerationFanActive(Boolean aerationFanActive) {
        this.aerationFanActive = aerationFanActive;
    }

    public SpoilageRiskLevel getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(SpoilageRiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }
}
