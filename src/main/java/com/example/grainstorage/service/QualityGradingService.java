package com.example.grainstorage.service;

import com.example.grainstorage.dto.response.QualityGradeResponse;
import com.example.grainstorage.entity.enums.GrainGrade;
import org.springframework.stereotype.Service;

@Service
public class QualityGradingService {

    /**
     * Automatically evaluates grain lot quality based on moisture and impurity parameters.
     * Grading Standard:
     * - Moisture <= 12% and Impurity <= 2% => GRADE_A (Accepted)
     * - Moisture <= 15% and Impurity <= 5% => SUB_STANDARD (Accepted for auxiliary distribution)
     * - Otherwise => REJECTED (Unfit for storage/distribution)
     *
     * Quality score calculation:
     * Base score: 100.0
     * Moisture penalty: (moisture - 10.0) * 2.5 (if > 10%)
     * Impurity penalty: impurity * 5.0
     * Final score clamped between 0 and 100.
     */
    public QualityGradeResponse evaluateQuality(Double moisturePercentage, Double impurityPercentage) {
        if (moisturePercentage == null || impurityPercentage == null) {
            throw new IllegalArgumentException("Moisture and impurity percentages must not be null");
        }

        GrainGrade grade;
        boolean accepted;
        String evaluation;

        if (moisturePercentage <= 12.0 && impurityPercentage <= 2.0) {
            grade = GrainGrade.GRADE_A;
            accepted = true;
            evaluation = "Premium Quality Grain - Meets all FCI/PDS Grade A standard specifications.";
        } else if (moisturePercentage <= 15.0 && impurityPercentage <= 5.0) {
            grade = GrainGrade.SUB_STANDARD;
            accepted = true;
            evaluation = "Sub-Standard Grain - Acceptable under fair average quality (FAQ) standards.";
        } else {
            grade = GrainGrade.REJECTED;
            accepted = false;
            evaluation = "Rejected - Moisture or impurity levels exceed permissible food safety limits.";
        }

        // Calculate quality score out of 100
        double moisturePenalty = (moisturePercentage > 10.0) ? (moisturePercentage - 10.0) * 2.5 : 0.0;
        double impurityPenalty = impurityPercentage * 5.0;
        double rawScore = 100.0 - (moisturePenalty + impurityPenalty);
        double qualityScore = Math.max(0.0, Math.min(100.0, Math.round(rawScore * 10.0) / 10.0));

        return new QualityGradeResponse(qualityScore, grade, accepted, evaluation);
    }
}
