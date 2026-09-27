package com.example.grainstorage.dto.response;

import com.example.grainstorage.entity.enums.WarehouseStatus;

public class WarehouseCapacityResponse {
    private Long warehouseId;
    private String warehouseCode;
    private String warehouseName;
    private String location;
    private Double totalCapacity;
    private Double usedCapacity;
    private Double availableCapacity;
    private Double utilizationPercentage;
    private WarehouseStatus status;

    public WarehouseCapacityResponse() {
    }

    public WarehouseCapacityResponse(Long warehouseId, String warehouseCode, String warehouseName, String location,
                                     Double totalCapacity, Double usedCapacity, Double availableCapacity, WarehouseStatus status) {
        this.warehouseId = warehouseId;
        this.warehouseCode = warehouseCode;
        this.warehouseName = warehouseName;
        this.location = location;
        this.totalCapacity = totalCapacity;
        this.usedCapacity = usedCapacity;
        this.availableCapacity = availableCapacity;
        this.status = status;
        this.utilizationPercentage = (totalCapacity != null && totalCapacity > 0)
                ? Math.round((usedCapacity / totalCapacity) * 10000.0) / 100.0
                : 0.0;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(Double totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public Double getUsedCapacity() {
        return usedCapacity;
    }

    public void setUsedCapacity(Double usedCapacity) {
        this.usedCapacity = usedCapacity;
    }

    public Double getAvailableCapacity() {
        return availableCapacity;
    }

    public void setAvailableCapacity(Double availableCapacity) {
        this.availableCapacity = availableCapacity;
    }

    public Double getUtilizationPercentage() {
        return utilizationPercentage;
    }

    public void setUtilizationPercentage(Double utilizationPercentage) {
        this.utilizationPercentage = utilizationPercentage;
    }

    public WarehouseStatus getStatus() {
        return status;
    }

    public void setStatus(WarehouseStatus status) {
        this.status = status;
    }
}
