package com.example.grainstorage.dto.response;

import com.example.grainstorage.entity.enums.GrainGrade;

public class QualityGradeResponse {
    private Double qualityScore;
    private GrainGrade grade;
    private boolean accepted;
    private String gradingEvaluation;

    public QualityGradeResponse() {
    }

    public QualityGradeResponse(Double qualityScore, GrainGrade grade, boolean accepted, String gradingEvaluation) {
        this.qualityScore = qualityScore;
        this.grade = grade;
        this.accepted = accepted;
        this.gradingEvaluation = gradingEvaluation;
    }

    public Double getQualityScore() {
        return qualityScore;
    }

    public void setQualityScore(Double qualityScore) {
        this.qualityScore = qualityScore;
    }

    public GrainGrade getGrade() {
        return grade;
    }

    public void setGrade(GrainGrade grade) {
        this.grade = grade;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getGradingEvaluation() {
        return gradingEvaluation;
    }

    public void setGradingEvaluation(String gradingEvaluation) {
        this.gradingEvaluation = gradingEvaluation;
    }
}
