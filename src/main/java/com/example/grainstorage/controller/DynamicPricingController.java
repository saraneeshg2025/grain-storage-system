package com.example.grainstorage.controller;

import com.example.grainstorage.dto.request.DynamicPricingRequest;
import com.example.grainstorage.dto.response.ApiResponse;
import com.example.grainstorage.dto.response.DynamicPricingResponse;
import com.example.grainstorage.service.DynamicPricingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pricing")
@CrossOrigin(origins = "*")
public class DynamicPricingController {

    private final DynamicPricingService dynamicPricingService;

    public DynamicPricingController(DynamicPricingService dynamicPricingService) {
        this.dynamicPricingService = dynamicPricingService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<ApiResponse<DynamicPricingResponse>> calculateProcurementPrice(@Valid @RequestBody DynamicPricingRequest request) {
        DynamicPricingResponse response = dynamicPricingService.calculateProcurementPrice(request);
        return ResponseEntity.ok(ApiResponse.ok("Procurement pricing & quality incentives computed successfully", response));
    }
}
