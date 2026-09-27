package com.example.grainstorage.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WarehouseCapacityReport {
    private LocalDateTime generatedAt;
    private int totalWarehouses = 0;
    private Double totalCapacity = 0.0;
    private Double totalUsedCapacity = 0.0;
    private Double totalAvailableCapacity = 0.0;
    private Double averageUtilizationPercentage = 0.0;
    private List<WarehouseCapacityResponse> warehouses = new ArrayList<>();

    public WarehouseCapacityReport() {
        this.generatedAt = LocalDateTime.now();
    }

    public void addWarehouse(WarehouseCapacityResponse wh) {
        this.warehouses.add(wh);
        this.totalWarehouses = this.warehouses.size();
        this.totalCapacity += (wh.getTotalCapacity() != null ? wh.getTotalCapacity() : 0.0);
        this.totalUsedCapacity += (wh.getUsedCapacity() != null ? wh.getUsedCapacity() : 0.0);
        this.totalAvailableCapacity += (wh.getAvailableCapacity() != null ? wh.getAvailableCapacity() : 0.0);
        if (this.totalCapacity > 0) {
            this.averageUtilizationPercentage = Math.round((this.totalUsedCapacity / this.totalCapacity) * 10000.0) / 100.0;
        }
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    public int getTotalWarehouses() {
        return totalWarehouses;
    }

    public void setTotalWarehouses(int totalWarehouses) {
        this.totalWarehouses = totalWarehouses;
    }

    public Double getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(Double totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public Double getTotalUsedCapacity() {
        return totalUsedCapacity;
    }

    public void setTotalUsedCapacity(Double totalUsedCapacity) {
        this.totalUsedCapacity = totalUsedCapacity;
    }

    public Double getTotalAvailableCapacity() {
        return totalAvailableCapacity;
    }

    public void setTotalAvailableCapacity(Double totalAvailableCapacity) {
        this.totalAvailableCapacity = totalAvailableCapacity;
    }

    public Double getAverageUtilizationPercentage() {
        return averageUtilizationPercentage;
    }

    public void setAverageUtilizationPercentage(Double averageUtilizationPercentage) {
        this.averageUtilizationPercentage = averageUtilizationPercentage;
    }

    public List<WarehouseCapacityResponse> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<WarehouseCapacityResponse> warehouses) {
        this.warehouses = warehouses;
    }
}
