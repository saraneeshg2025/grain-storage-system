package com.example.grainstorage.controller;

import com.example.grainstorage.dto.request.VendorBillRequest;
import com.example.grainstorage.dto.response.ApiResponse;
import com.example.grainstorage.entity.VendorBill;
import com.example.grainstorage.service.VendorBillService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor-bills")
@CrossOrigin(origins = "*")
public class VendorBillController {

    private final VendorBillService vendorBillService;

    public VendorBillController(VendorBillService vendorBillService) {
        this.vendorBillService = vendorBillService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<VendorBill>> createVendorBill(@Valid @RequestBody VendorBillRequest request) {
        VendorBill bill = vendorBillService.createVendorBill(request);
        return new ResponseEntity<>(ApiResponse.ok("Vendor Bill created and purchase journal posted", bill), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<VendorBill>>> getAllVendorBills() {
        List<VendorBill> bills = vendorBillService.getAllVendorBills();
        return ResponseEntity.ok(ApiResponse.ok(bills));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VendorBill>> getVendorBillById(@PathVariable Long id) {
        VendorBill bill = vendorBillService.getVendorBillById(id);
        return ResponseEntity.ok(ApiResponse.ok(bill));
    }
}
