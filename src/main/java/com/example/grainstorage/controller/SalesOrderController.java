package com.example.grainstorage.controller;

import com.example.grainstorage.dto.request.SalesOrderRequest;
import com.example.grainstorage.dto.response.ApiResponse;
import com.example.grainstorage.entity.CustomerInvoice;
import com.example.grainstorage.entity.SalesOrder;
import com.example.grainstorage.entity.enums.SalesOrderStatus;
import com.example.grainstorage.service.SalesOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales-orders")
@CrossOrigin(origins = "*")
public class SalesOrderController {

    private final SalesOrderService salesOrderService;

    public SalesOrderController(SalesOrderService salesOrderService) {
        this.salesOrderService = salesOrderService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SalesOrder>> createSalesOrder(@Valid @RequestBody SalesOrderRequest request) {
        SalesOrder order = salesOrderService.createSalesOrder(request);
        return new ResponseEntity<>(ApiResponse.ok("Sales Order created successfully", order), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SalesOrder>>> getAllSalesOrders() {
        List<SalesOrder> orders = salesOrderService.getAllSalesOrders();
        return ResponseEntity.ok(ApiResponse.ok(orders));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SalesOrder>> getSalesOrderById(@PathVariable Long id) {
        SalesOrder order = salesOrderService.getSalesOrderById(id);
        return ResponseEntity.ok(ApiResponse.ok(order));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<SalesOrder>> updateStatus(@PathVariable Long id, @RequestParam SalesOrderStatus status) {
        SalesOrder updated = salesOrderService.updateStatus(id, status);
        return ResponseEntity.ok(ApiResponse.ok("Sales order status updated to " + status, updated));
    }

    @PostMapping("/{id}/dispatch")
    public ResponseEntity<ApiResponse<CustomerInvoice>> dispatchOrder(@PathVariable Long id) {
        CustomerInvoice invoice = salesOrderService.dispatchOrder(id);
        return ResponseEntity.ok(ApiResponse.ok("Grain dispatched successfully! Inventory reduced, warehouse capacity freed, and invoice generated.", invoice));
    }
}
