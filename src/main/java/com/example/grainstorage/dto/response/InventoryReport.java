package com.example.grainstorage.dto.response;

import com.example.grainstorage.entity.enums.GrainGrade;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InventoryReport {
    private LocalDateTime generatedAt;
    private int totalRecords = 0;
    private Double totalQuantity = 0.0;
    private List<InventoryItemDetail> items = new ArrayList<>();

    public InventoryReport() {
        this.generatedAt = LocalDateTime.now();
    }

    public static class InventoryItemDetail {
        private Long inventoryId;
        private Long warehouseId;
        private String warehouseName;
        private Long productId;
        private String productName;
        private String grainType;
        private String lotNumber;
        private Double quantity;
        private String unit;
        private GrainGrade grade;
        private LocalDateTime lastUpdated;

        public InventoryItemDetail() {
        }

        public InventoryItemDetail(Long inventoryId, Long warehouseId, String warehouseName,
                                   Long productId, String productName, String grainType,
                                   String lotNumber, Double quantity, String unit,
                                   GrainGrade grade, LocalDateTime lastUpdated) {
            this.inventoryId = inventoryId;
            this.warehouseId = warehouseId;
            this.warehouseName = warehouseName;
            this.productId = productId;
            this.productName = productName;
            this.grainType = grainType;
            this.lotNumber = lotNumber;
            this.quantity = quantity;
            this.unit = unit;
            this.grade = grade;
            this.lastUpdated = lastUpdated;
        }

        public Long getInventoryId() {
            return inventoryId;
        }

        public void setInventoryId(Long inventoryId) {
            this.inventoryId = inventoryId;
        }

        public Long getWarehouseId() {
            return warehouseId;
        }

        public void setWarehouseId(Long warehouseId) {
            this.warehouseId = warehouseId;
        }

        public String getWarehouseName() {
            return warehouseName;
        }

        public void setWarehouseName(String warehouseName) {
            this.warehouseName = warehouseName;
        }

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getGrainType() {
            return grainType;
        }

        public void setGrainType(String grainType) {
            this.grainType = grainType;
        }

        public String getLotNumber() {
            return lotNumber;
        }

        public void setLotNumber(String lotNumber) {
            this.lotNumber = lotNumber;
        }

        public Double getQuantity() {
            return quantity;
        }

        public void setQuantity(Double quantity) {
            this.quantity = quantity;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public GrainGrade getGrade() {
            return grade;
        }

        public void setGrade(GrainGrade grade) {
            this.grade = grade;
        }

        public LocalDateTime getLastUpdated() {
            return lastUpdated;
        }

        public void setLastUpdated(LocalDateTime lastUpdated) {
            this.lastUpdated = lastUpdated;
        }
    }

    public void addItem(InventoryItemDetail item) {
        this.items.add(item);
        this.totalRecords = this.items.size();
        this.totalQuantity += (item.getQuantity() != null ? item.getQuantity() : 0.0);
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public Double getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public List<InventoryItemDetail> getItems() {
        return items;
    }

    public void setItems(List<InventoryItemDetail> items) {
        this.items = items;
    }
}
