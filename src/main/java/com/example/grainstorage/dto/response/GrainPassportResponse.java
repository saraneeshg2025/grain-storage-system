package com.example.grainstorage.dto.response;

import com.example.grainstorage.entity.enums.GrainGrade;
import com.example.grainstorage.entity.enums.LotStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GrainPassportResponse {
    private String lotNumber;
    private String productName;
    private String grainType;
    private Double quantityKg;
    private GrainGrade certifiedGrade;
    private Double qualityScore;
    private Double moisturePercentage;
    private Double impurityPercentage;
    private String farmerCooperative;
    private String warehouseName;
    private String siloLocation;
    private LocalDateTime procurementDate;
    private String cryptographicBatchHash;
    private boolean tamperProofVerified;
    private int estimatedSafeStorageDaysRemaining;
    private LotStatus currentStatus;
    private List<JourneyMilestone> journey = new ArrayList<>();

    public GrainPassportResponse() {
    }

    public static class JourneyMilestone {
        private String stage;
        private String location;
        private String timestamp;
        private String status;
        private String verifiedBy;

        public JourneyMilestone() {
        }

        public JourneyMilestone(String stage, String location, String timestamp, String status, String verifiedBy) {
            this.stage = stage;
            this.location = location;
            this.timestamp = timestamp;
            this.status = status;
            this.verifiedBy = verifiedBy;
        }

        public String getStage() {
            return stage;
        }

        public void setStage(String stage) {
            this.stage = stage;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getVerifiedBy() {
            return verifiedBy;
        }

        public void setVerifiedBy(String verifiedBy) {
            this.verifiedBy = verifiedBy;
        }
    }

    public void addMilestone(String stage, String location, String timestamp, String status, String verifiedBy) {
        this.journey.add(new JourneyMilestone(stage, location, timestamp, status, verifiedBy));
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
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

    public Double getQuantityKg() {
        return quantityKg;
    }

    public void setQuantityKg(Double quantityKg) {
        this.quantityKg = quantityKg;
    }

    public GrainGrade getCertifiedGrade() {
        return certifiedGrade;
    }

    public void setCertifiedGrade(GrainGrade certifiedGrade) {
        this.certifiedGrade = certifiedGrade;
    }

    public Double getQualityScore() {
        return qualityScore;
    }

    public void setQualityScore(Double qualityScore) {
        this.qualityScore = qualityScore;
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

    public String getFarmerCooperative() {
        return farmerCooperative;
    }

    public void setFarmerCooperative(String farmerCooperative) {
        this.farmerCooperative = farmerCooperative;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getSiloLocation() {
        return siloLocation;
    }

    public void setSiloLocation(String siloLocation) {
        this.siloLocation = siloLocation;
    }

    public LocalDateTime getProcurementDate() {
        return procurementDate;
    }

    public void setProcurementDate(LocalDateTime procurementDate) {
        this.procurementDate = procurementDate;
    }

    public String getCryptographicBatchHash() {
        return cryptographicBatchHash;
    }

    public void setCryptographicBatchHash(String cryptographicBatchHash) {
        this.cryptographicBatchHash = cryptographicBatchHash;
    }

    public boolean isTamperProofVerified() {
        return tamperProofVerified;
    }

    public void setTamperProofVerified(boolean tamperProofVerified) {
        this.tamperProofVerified = tamperProofVerified;
    }

    public int getEstimatedSafeStorageDaysRemaining() {
        return estimatedSafeStorageDaysRemaining;
    }

    public void setEstimatedSafeStorageDaysRemaining(int estimatedSafeStorageDaysRemaining) {
        this.estimatedSafeStorageDaysRemaining = estimatedSafeStorageDaysRemaining;
    }

    public LotStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(LotStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public List<JourneyMilestone> getJourney() {
        return journey;
    }

    public void setJourney(List<JourneyMilestone> journey) {
        this.journey = journey;
    }
}
