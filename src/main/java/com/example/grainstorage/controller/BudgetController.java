package com.example.grainstorage.controller;

import com.example.grainstorage.dto.request.BudgetRequest;
import com.example.grainstorage.dto.response.ApiResponse;
import com.example.grainstorage.dto.response.BudgetVarianceReport;
import com.example.grainstorage.entity.Budget;
import com.example.grainstorage.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@CrossOrigin(origins = "*")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Budget>> createBudget(@Valid @RequestBody BudgetRequest request) {
        Budget budget = budgetService.createBudget(request);
        return new ResponseEntity<>(ApiResponse.ok("Budget created successfully", budget), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Budget>>> getAllBudgets() {
        List<Budget> budgets = budgetService.getAllBudgets();
        return ResponseEntity.ok(ApiResponse.ok(budgets));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Budget>> getBudgetById(@PathVariable Long id) {
        Budget budget = budgetService.getBudgetById(id);
        return ResponseEntity.ok(ApiResponse.ok(budget));
    }

    @GetMapping("/variance")
    public ResponseEntity<ApiResponse<BudgetVarianceReport>> getBudgetVarianceReport() {
        BudgetVarianceReport report = budgetService.getBudgetVarianceReport();
        return ResponseEntity.ok(ApiResponse.ok(report));
    }
}
