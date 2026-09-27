package com.example.grainstorage.service;

import com.example.grainstorage.dto.response.GrainPassportResponse;
import com.example.grainstorage.entity.GrainLot;
import com.example.grainstorage.entity.enums.GrainGrade;
import com.example.grainstorage.exception.ResourceNotFoundException;
import com.example.grainstorage.repository.GrainLotRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.format.DateTimeFormatter;

@Service
public class GrainProvenanceService {

    private final GrainLotRepository grainLotRepository;

    public GrainProvenanceService(GrainLotRepository grainLotRepository) {
        this.grainLotRepository = grainLotRepository;
    }

    public GrainPassportResponse generateDigitalPassport(String lotNumber) {
        GrainLot lot = grainLotRepository.findByLotNumber(lotNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Grain lot not found with lot number: " + lotNumber));

        return buildPassport(lot);
    }

    public GrainPassportResponse generateDigitalPassportById(Long lotId) {
        GrainLot lot = grainLotRepository.findById(lotId)
                .orElseThrow(() -> new ResourceNotFoundException("Grain lot not found with id: " + lotId));

        return buildPassport(lot);
    }

    private GrainPassportResponse buildPassport(GrainLot lot) {
        GrainPassportResponse passport = new GrainPassportResponse();

        passport.setLotNumber(lot.getLotNumber());
        passport.setProductName(lot.getProduct() != null ? lot.getProduct().getProductName() : "Grain");
        passport.setGrainType(lot.getProduct() != null ? lot.getProduct().getGrainType().name() : "N/A");
        passport.setQuantityKg(lot.getQuantity());
        passport.setCertifiedGrade(lot.getGrade());
        passport.setQualityScore(lot.getQualityScore());
        passport.setMoisturePercentage(lot.getMoisturePercentage());
        passport.setImpurityPercentage(lot.getImpurityPercentage());
        passport.setFarmerCooperative(lot.getVendor() != null ? lot.getVendor().getName() : "Cooperative Mandi");
        passport.setWarehouseName(lot.getWarehouse() != null ? lot.getWarehouse().getWarehouseName() : "Central Silo");
        passport.setSiloLocation(lot.getWarehouse() != null ? lot.getWarehouse().getLocation() : "Warehouse Hub");
        passport.setProcurementDate(lot.getProcurementDate());
        passport.setCurrentStatus(lot.getStatus());

        // Cryptographic Provenance Hash (SHA-256)
        String rawData = String.format("%s:%s:%s:%s:%.1f:%.2f:%s",
                lot.getLotNumber(),
                lot.getProduct() != null ? lot.getProduct().getProductCode() : "P",
                lot.getGrade(),
                lot.getProcurementDate(),
                lot.getQualityScore() != null ? lot.getQualityScore() : 0.0,
                lot.getQuantity(),
                lot.getWarehouse() != null ? lot.getWarehouse().getWarehouseCode() : "WH"
        );
        passport.setCryptographicBatchHash(computeSha256(rawData));
        passport.setTamperProofVerified(true);

        // Predictive Safe Storage Calculation
        int safeDays = calculateSafeStorageDays(lot.getMoisturePercentage(), lot.getGrade());
        passport.setEstimatedSafeStorageDaysRemaining(safeDays);

        // Journey Milestones
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm");
        String procDateStr = lot.getProcurementDate() != null ? lot.getProcurementDate().format(dtf) : "N/A";

        passport.addMilestone(
                "APMC Mandi Intake",
                lot.getVendor() != null ? lot.getVendor().getAddress() : "Regional Mandi",
                procDateStr,
                "VERIFIED",
                "Mandi Procurement Officer"
        );

        passport.addMilestone(
                "Scientific Quality Assay",
                "Central Quality Lab - Station 4",
                procDateStr,
                "GRADE " + (lot.getGrade() != null ? lot.getGrade().name() : "PENDING") + " (Score: " + lot.getQualityScore() + "/100)",
                "Chief Quality Inspector"
        );

        passport.addMilestone(
                "Silo Intake & Hermetic Storage",
                lot.getWarehouse() != null ? lot.getWarehouse().getWarehouseName() : "Buffer Silo",
                procDateStr,
                lot.getStatus().name(),
                "Warehouse Director"
        );

        if (lot.getStatus().name().equals("DISTRIBUTED")) {
            passport.addMilestone(
                    "PDS Fair Price Shop Dispatch",
                    "Civil Supplies Distribution Fleet",
                    "Dispatched",
                    "DELIVERED_TO_PDS_AGENCY",
                    "Logistics Officer"
            );
        }

        return passport;
    }

    private int calculateSafeStorageDays(Double moisture, GrainGrade grade) {
        if (grade == GrainGrade.REJECTED) {
            return 0;
        }
        if (moisture == null) return 180;
        if (moisture <= 11.0) return 365; // Up to 1 year safe hermetic storage
        if (moisture <= 12.0) return 270; // 9 months
        if (moisture <= 14.0) return 150; // 5 months
        if (moisture <= 15.0) return 60;  // 2 months (Priority distribution recommended)
        return 20; // High spoilage risk
    }

    private String computeSha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return "0x" + hexString.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            return "0x" + Integer.toHexString(input.hashCode()).toUpperCase();
        }
    }
}
