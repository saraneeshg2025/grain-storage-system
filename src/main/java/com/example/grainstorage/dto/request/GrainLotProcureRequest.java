package com.example.grainstorage.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class GrainLotProcureRequest {

    private String lotNumber;

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Vendor ID is required")
    private Long vendorId;

    @NotNull(message = "Warehouse ID is required")
    private Long warehouseId;

    @NotNull(message = "Quantity is required")
    @DecimalMin(value = "0.01", message = "Quantity must be greater than zero")
    private Double quantity;

    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.0", message = "Unit price must be non-negative")
    private Double unitPrice;

    @NotNull(message = "Moisture percentage is required")
    @DecimalMin(value = "0.0", message = "Moisture cannot be negative")
    @DecimalMax(value = "100.0", message = "Moisture cannot exceed 100%")
    private Double moisturePercentage;

    @NotNull(message = "Impurity percentage is required")
    @DecimalMin(value = "0.0", message = "Impurity cannot be negative")
    @DecimalMax(value = "100.0", message = "Impurity cannot exceed 100%")
    private Double impurityPercentage;

    private Boolean autoStore = false;

    public GrainLotProcureRequest() {
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
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

    public Boolean getAutoStore() {
        return autoStore;
    }

    public void setAutoStore(Boolean autoStore) {
        this.autoStore = autoStore;
    }
}
