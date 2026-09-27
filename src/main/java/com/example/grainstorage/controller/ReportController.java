package com.example.grainstorage.controller;

import com.example.grainstorage.dto.response.*;
import com.example.grainstorage.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/balance-sheet")
    public ResponseEntity<ApiResponse<BalanceSheetReport>> getBalanceSheet() {
        BalanceSheetReport report = reportService.generateBalanceSheet();
        return ResponseEntity.ok(ApiResponse.ok("Balance Sheet generated successfully", report));
    }

    @GetMapping("/profit-loss")
    public ResponseEntity<ApiResponse<ProfitLossReport>> getProfitLossReport() {
        ProfitLossReport report = reportService.generateProfitLossReport();
        return ResponseEntity.ok(ApiResponse.ok("Profit & Loss Statement generated successfully", report));
    }

    @GetMapping("/budget")
    public ResponseEntity<ApiResponse<BudgetVarianceReport>> getBudgetReport() {
        BudgetVarianceReport report = reportService.generateBudgetReport();
        return ResponseEntity.ok(ApiResponse.ok("Budget Variance Report generated successfully", report));
    }

    @GetMapping("/warehouse-capacity")
    public ResponseEntity<ApiResponse<WarehouseCapacityReport>> getWarehouseCapacityReport() {
        WarehouseCapacityReport report = reportService.generateWarehouseCapacityReport();
        return ResponseEntity.ok(ApiResponse.ok("Warehouse Capacity Report generated successfully", report));
    }

    @GetMapping("/inventory")
    public ResponseEntity<ApiResponse<InventoryReport>> getInventoryReport() {
        InventoryReport report = reportService.generateInventoryReport();
        return ResponseEntity.ok(ApiResponse.ok("Inventory Report generated successfully", report));
    }
}
