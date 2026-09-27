package com.example.grainstorage.controller;

import com.example.grainstorage.dto.request.GrainLotProcureRequest;
import com.example.grainstorage.dto.request.QualityGradeRequest;
import com.example.grainstorage.dto.response.ApiResponse;
import com.example.grainstorage.entity.GrainLot;
import com.example.grainstorage.service.GrainLotService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grain-lots")
@CrossOrigin(origins = "*")
public class GrainLotController {

    private final GrainLotService grainLotService;

    public GrainLotController(GrainLotService grainLotService) {
        this.grainLotService = grainLotService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GrainLot>> procureGrainLot(@Valid @RequestBody GrainLotProcureRequest request) {
        GrainLot grainLot = grainLotService.procureGrainLot(request);
        return new ResponseEntity<>(ApiResponse.ok("Grain lot procured and graded successfully", grainLot), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/grade")
    public ResponseEntity<ApiResponse<GrainLot>> gradeGrainLot(@PathVariable Long id, @Valid @RequestBody QualityGradeRequest request) {
        GrainLot gradedLot = grainLotService.gradeGrainLot(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Quality grading re-evaluated successfully", gradedLot));
    }

    @PostMapping("/{id}/store")
    public ResponseEntity<ApiResponse<GrainLot>> storeGrainLot(@PathVariable Long id) {
        GrainLot storedLot = grainLotService.storeGrainLot(id);
        return ResponseEntity.ok(ApiResponse.ok("Grain lot stored successfully in warehouse inventory", storedLot));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GrainLot>>> getAllGrainLots() {
        List<GrainLot> lots = grainLotService.getAllGrainLots();
        return ResponseEntity.ok(ApiResponse.ok(lots));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GrainLot>> getGrainLotById(@PathVariable Long id) {
        GrainLot lot = grainLotService.getGrainLotById(id);
        return ResponseEntity.ok(ApiResponse.ok(lot));
    }

    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<ApiResponse<List<GrainLot>>> getGrainLotsByWarehouse(@PathVariable Long warehouseId) {
        List<GrainLot> lots = grainLotService.getGrainLotsByWarehouse(warehouseId);
        return ResponseEntity.ok(ApiResponse.ok(lots));
    }
}
