package com.example.grainstorage.service;

import com.example.grainstorage.dto.request.DynamicPricingRequest;
import com.example.grainstorage.dto.response.DynamicPricingResponse;
import com.example.grainstorage.dto.response.QualityGradeResponse;
import com.example.grainstorage.entity.enums.GrainGrade;
import com.example.grainstorage.entity.enums.GrainType;
import org.springframework.stereotype.Service;

@Service
public class DynamicPricingService {

    private final QualityGradingService qualityGradingService;

    public DynamicPricingService(QualityGradingService qualityGradingService) {
        this.qualityGradingService = qualityGradingService;
    }

    /**
     * Calculates Government Minimum Support Price (MSP) with quality-linked incentives/penalties.
     */
    public DynamicPricingResponse calculateProcurementPrice(DynamicPricingRequest request) {
        double baseMspPerKg;
        GrainType type = request.getGrainType();

        if (type == GrainType.WHEAT) {
            baseMspPerKg = 22.75; // ₹2,275 per quintal
        } else if (type == GrainType.RICE) {
            baseMspPerKg = 23.00; // ₹2,300 per quintal
        } else if (type == GrainType.MAIZE) {
            baseMspPerKg = 20.90; // ₹2,090 per quintal
        } else {
            baseMspPerKg = 21.50;
        }

        // Automatic Quality Evaluation
        QualityGradeResponse gradeInfo = qualityGradingService.evaluateQuality(
                request.getMoisturePercentage(),
                request.getImpurityPercentage()
        );

        double quantity = request.getQuantityKg();
        double grossBase = quantity * baseMspPerKg;
        double incentiveBonus = 0.0;
        double penalty = 0.0;
        double netPayable;
        boolean dbtEligible;
        String note;

        if (gradeInfo.getGrade() == GrainGrade.GRADE_A) {
            if (request.getMoisturePercentage() <= 11.0 && request.getImpurityPercentage() <= 1.0) {
                // Exceptional dryness and purity bonus (4% premium)
                incentiveBonus = Math.round(grossBase * 0.04 * 100.0) / 100.0;
                note = "Premium Grade A Certified - 4% Quality Incentive Bonus credited for low moisture and minimal dockage.";
            } else {
                note = "Standard Grade A Approved - 100% MSP payout credited.";
            }
            netPayable = grossBase + incentiveBonus;
            dbtEligible = true;
        } else if (gradeInfo.getGrade() == GrainGrade.SUB_STANDARD) {
            // Dockage deduction for additional handling and drying
            penalty = Math.round(grossBase * 0.06 * 100.0) / 100.0;
            netPayable = grossBase - penalty;
            dbtEligible = true;
            note = "Sub-Standard Grain - 6% Dockage Deduction applied for drying and secondary cleaning.";
        } else {
            // Rejected
            penalty = grossBase;
            netPayable = 0.0;
            dbtEligible = false;
            note = "REJECTED - Grain failed statutory moisture / impurity thresholds. Ineligible for public procurement.";
        }

        double effectiveRate = (quantity > 0) ? Math.round((netPayable / quantity) * 100.0) / 100.0 : 0.0;

        return new DynamicPricingResponse(
                type,
                quantity,
                baseMspPerKg,
                gradeInfo.getGrade(),
                gradeInfo.getQualityScore(),
                Math.round(grossBase * 100.0) / 100.0,
                incentiveBonus,
                penalty,
                Math.round(netPayable * 100.0) / 100.0,
                effectiveRate,
                dbtEligible,
                note
        );
    }
}
