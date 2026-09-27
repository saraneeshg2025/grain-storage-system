package com.example.grainstorage.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class QualityGradeRequest {

    @NotNull(message = "Moisture percentage is required")
    @DecimalMin(value = "0.0", message = "Moisture cannot be negative")
    @DecimalMax(value = "100.0", message = "Moisture cannot exceed 100%")
    private Double moisturePercentage;

    @NotNull(message = "Impurity percentage is required")
    @DecimalMin(value = "0.0", message = "Impurity cannot be negative")
    @DecimalMax(value = "100.0", message = "Impurity cannot exceed 100%")
    private Double impurityPercentage;

    public QualityGradeRequest() {
    }

    public QualityGradeRequest(Double moisturePercentage, Double impurityPercentage) {
        this.moisturePercentage = moisturePercentage;
        this.impurityPercentage = impurityPercentage;
    }

    public Double getMoisturePercentage() {
        return moisturePercentage;
    }

    public void setMoisturePercentage(Double moisturePercentage) {
        this.moisturePercentage = moisturePercentage;
    }

    public Double getImpurityPercentage() {
        return impurityPercentage;
    }

    public void setImpurityPercentage(Double impurityPercentage) {
        this.impurityPercentage = impurityPercentage;
    }
}
