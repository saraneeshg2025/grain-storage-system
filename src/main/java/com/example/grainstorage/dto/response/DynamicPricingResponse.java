package com.example.grainstorage.dto.response;

import com.example.grainstorage.entity.enums.GrainGrade;
import com.example.grainstorage.entity.enums.GrainType;

public class DynamicPricingResponse {
    private GrainType grainType;
    private Double quantityKg;
    private Double baseMspPerKg;
    private GrainGrade grade;
    private Double qualityScore;
    private Double grossBaseAmount;
    private Double qualityIncentiveBonus;
    private Double penaltyDeduction;
    private Double netPayableAmount;
    private Double effectiveRatePerKg;
    private boolean dbtEligible;
    private String pricingPolicyNote;

    public DynamicPricingResponse() {
    }

    public DynamicPricingResponse(GrainType grainType, Double quantityKg, Double baseMspPerKg, GrainGrade grade,
                                  Double qualityScore, Double grossBaseAmount, Double qualityIncentiveBonus,
                                  Double penaltyDeduction, Double netPayableAmount, Double effectiveRatePerKg,
                                  boolean dbtEligible, String pricingPolicyNote) {
        this.grainType = grainType;
        this.quantityKg = quantityKg;
        this.baseMspPerKg = baseMspPerKg;
        this.grade = grade;
        this.qualityScore = qualityScore;
        this.grossBaseAmount = grossBaseAmount;
        this.qualityIncentiveBonus = qualityIncentiveBonus;
        this.penaltyDeduction = penaltyDeduction;
        this.netPayableAmount = netPayableAmount;
        this.effectiveRatePerKg = effectiveRatePerKg;
        this.dbtEligible = dbtEligible;
        this.pricingPolicyNote = pricingPolicyNote;
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

    public Double getBaseMspPerKg() {
        return baseMspPerKg;
    }

    public void setBaseMspPerKg(Double baseMspPerKg) {
        this.baseMspPerKg = baseMspPerKg;
    }

    public GrainGrade getGrade() {
        return grade;
    }

    public void setGrade(GrainGrade grade) {
        this.grade = grade;
    }

    public Double getQualityScore() {
        return qualityScore;
    }

    public void setQualityScore(Double qualityScore) {
        this.qualityScore = qualityScore;
    }

    public Double getGrossBaseAmount() {
        return grossBaseAmount;
    }

    public void setGrossBaseAmount(Double grossBaseAmount) {
        this.grossBaseAmount = grossBaseAmount;
    }

    public Double getQualityIncentiveBonus() {
        return qualityIncentiveBonus;
    }

    public void setQualityIncentiveBonus(Double qualityIncentiveBonus) {
        this.qualityIncentiveBonus = qualityIncentiveBonus;
    }

    public Double getPenaltyDeduction() {
        return penaltyDeduction;
    }

    public void setPenaltyDeduction(Double penaltyDeduction) {
        this.penaltyDeduction = penaltyDeduction;
    }

    public Double getNetPayableAmount() {
        return netPayableAmount;
    }

    public void setNetPayableAmount(Double netPayableAmount) {
        this.netPayableAmount = netPayableAmount;
    }

    public Double getEffectiveRatePerKg() {
        return effectiveRatePerKg;
    }

    public void setEffectiveRatePerKg(Double effectiveRatePerKg) {
        this.effectiveRatePerKg = effectiveRatePerKg;
    }

    public boolean isDbtEligible() {
        return dbtEligible;
    }

    public void setDbtEligible(boolean dbtEligible) {
        this.dbtEligible = dbtEligible;
    }

    public String getPricingPolicyNote() {
        return pricingPolicyNote;
    }

    public void setPricingPolicyNote(String pricingPolicyNote) {
        this.pricingPolicyNote = pricingPolicyNote;
    }
}
