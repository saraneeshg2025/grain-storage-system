package com.example.grainstorage.controller;

import com.example.grainstorage.dto.response.ApiResponse;
import com.example.grainstorage.dto.response.GrainPassportResponse;
import com.example.grainstorage.service.GrainProvenanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/provenance")
@CrossOrigin(origins = "*")
public class GrainProvenanceController {

    private final GrainProvenanceService provenanceService;

    public GrainProvenanceController(GrainProvenanceService provenanceService) {
        this.provenanceService = provenanceService;
    }

    @GetMapping("/{lotNumber}")
    public ResponseEntity<ApiResponse<GrainPassportResponse>> getGrainPassport(@PathVariable String lotNumber) {
        GrainPassportResponse passport = provenanceService.generateDigitalPassport(lotNumber);
        return ResponseEntity.ok(ApiResponse.ok("Digital Grain Passport & cryptographic provenance verified", passport));
    }

    @GetMapping("/id/{lotId}")
    public ResponseEntity<ApiResponse<GrainPassportResponse>> getGrainPassportById(@PathVariable Long lotId) {
        GrainPassportResponse passport = provenanceService.generateDigitalPassportById(lotId);
        return ResponseEntity.ok(ApiResponse.ok("Digital Grain Passport & cryptographic provenance verified", passport));
    }
}
