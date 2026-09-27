package com.example.grainstorage.controller;

import com.example.grainstorage.dto.request.PurchaseOrderRequest;
import com.example.grainstorage.dto.response.ApiResponse;
import com.example.grainstorage.entity.PurchaseOrder;
import com.example.grainstorage.entity.enums.PurchaseOrderStatus;
import com.example.grainstorage.service.PurchaseOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
@CrossOrigin(origins = "*")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PurchaseOrder>> createPurchaseOrder(@Valid @RequestBody PurchaseOrderRequest request) {
        PurchaseOrder po = purchaseOrderService.createPurchaseOrder(request);
        return new ResponseEntity<>(ApiResponse.ok("Purchase Order created successfully", po), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PurchaseOrder>>> getAllPurchaseOrders() {
        List<PurchaseOrder> list = purchaseOrderService.getAllPurchaseOrders();
        return ResponseEntity.ok(ApiResponse.ok(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PurchaseOrder>> getPurchaseOrderById(@PathVariable Long id) {
        PurchaseOrder po = purchaseOrderService.getPurchaseOrderById(id);
        return ResponseEntity.ok(ApiResponse.ok(po));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<PurchaseOrder>> updateStatus(@PathVariable Long id, @RequestParam PurchaseOrderStatus status) {
        PurchaseOrder updatedPo = purchaseOrderService.updateStatus(id, status);
        return ResponseEntity.ok(ApiResponse.ok("Purchase Order status updated to " + status, updatedPo));
    }
}
