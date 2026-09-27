package com.example.grainstorage.controller;

import com.example.grainstorage.dto.request.SiloTelemetryRequest;
import com.example.grainstorage.dto.response.ApiResponse;
import com.example.grainstorage.entity.SiloTelemetry;
import com.example.grainstorage.service.SiloTelemetryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/telemetry")
@CrossOrigin(origins = "*")
public class SiloTelemetryController {

    private final SiloTelemetryService telemetryService;

    public SiloTelemetryController(SiloTelemetryService telemetryService) {
        this.telemetryService = telemetryService;
    }

    @PostMapping("/record")
    public ResponseEntity<ApiResponse<SiloTelemetry>> recordTelemetry(@Valid @RequestBody SiloTelemetryRequest request) {
        SiloTelemetry telemetry = telemetryService.recordTelemetry(request);
        return new ResponseEntity<>(ApiResponse.ok("IoT telemetry recorded and micro-climate analyzed", telemetry), HttpStatus.CREATED);
    }

    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<ApiResponse<List<SiloTelemetry>>> getTelemetryHistory(@PathVariable Long warehouseId) {
        List<SiloTelemetry> list = telemetryService.getTelemetryForWarehouse(warehouseId);
        return ResponseEntity.ok(ApiResponse.ok(list));
    }

    @GetMapping("/warehouse/{warehouseId}/latest")
    public ResponseEntity<ApiResponse<SiloTelemetry>> getLatestTelemetry(@PathVariable Long warehouseId) {
        SiloTelemetry latest = telemetryService.getLatestTelemetry(warehouseId);
        return ResponseEntity.ok(ApiResponse.ok(latest));
    }

    @GetMapping("/alerts")
    public ResponseEntity<ApiResponse<List<SiloTelemetry>>> getActiveRiskAlerts() {
        List<SiloTelemetry> alerts = telemetryService.getActiveRiskAlerts();
        return ResponseEntity.ok(ApiResponse.ok(alerts));
    }
}
