package com.example.grainstorage;

import com.example.grainstorage.dto.request.GrainLotProcureRequest;
import com.example.grainstorage.dto.response.QualityGradeResponse;
import com.example.grainstorage.entity.enums.GrainGrade;
import com.example.grainstorage.service.QualityGradingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("h2")
class GrainStorageApplicationTests {

    @Autowired
    private QualityGradingService qualityGradingService;

    @Test
    void contextLoads() {
        assertNotNull(qualityGradingService);
    }

    @Test
    @DisplayName("Quality Grading: Grade A evaluation with moisture <= 12% and impurity <= 2%")
    void testQualityGradingGradeA() {
        QualityGradeResponse response = qualityGradingService.evaluateQuality(11.5, 1.5);
        assertEquals(GrainGrade.GRADE_A, response.getGrade());
        assertTrue(response.isAccepted());
        assertTrue(response.getQualityScore() >= 80.0);
    }

    @Test
    @DisplayName("Quality Grading: Sub-Standard evaluation with moisture <= 15% and impurity <= 5%")
    void testQualityGradingSubStandard() {
        QualityGradeResponse response = qualityGradingService.evaluateQuality(14.0, 3.5);
        assertEquals(GrainGrade.SUB_STANDARD, response.getGrade());
        assertTrue(response.isAccepted());
    }

    @Test
    @DisplayName("Quality Grading: Rejected evaluation with high moisture or impurity")
    void testQualityGradingRejected() {
        QualityGradeResponse response = qualityGradingService.evaluateQuality(18.0, 6.0);
        assertEquals(GrainGrade.REJECTED, response.getGrade());
        assertFalse(response.isAccepted());
    }
}
