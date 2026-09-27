package com.example.grainstorage.dto.request;

import com.example.grainstorage.entity.enums.GrainType;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class DynamicPricingRequest {

    @NotNull(message = "Grain type is required (WHEAT, RICE, MAIZE)")
    private GrainType grainType;

    @NotNull(message = "Quantity in kg is required")
    @DecimalMin(value = "1.0", message = "Quantity must be at least 1 kg")
    private Double quantityKg;

    @NotNull(message = "Moisture percentage is required")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    private Double moisturePercentage;

    @NotNull(message = "Impurity percentage is required")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    private Double impurityPercentage;

    public DynamicPricingRequest() {
    }

    public DynamicPricingRequest(GrainType grainType, Double quantityKg, Double moisturePercentage, Double impurityPercentage) {
        this.grainType = grainType;
        this.quantityKg = quantityKg;
        this.moisturePercentage = moisturePercentage;
        this.impurityPercentage = impurityPercentage;
    }

    public GrainType getGrainType() {
        return grainType;
    }

    public void setGrainType(GrainType grainType) {
        this.grainType = grainType;
    }

    public Double getQuantityKg() {
        return quantityKg;
    }

    public void setQuantityKg(Double quantityKg) {
        this.quantityKg = quantityKg;
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
