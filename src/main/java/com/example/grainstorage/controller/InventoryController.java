package com.example.grainstorage.controller;

import com.example.grainstorage.dto.response.ApiResponse;
import com.example.grainstorage.entity.Inventory;
import com.example.grainstorage.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "*")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Inventory>>> getAllInventory() {
        List<Inventory> list = inventoryService.getAllInventory();
        return ResponseEntity.ok(ApiResponse.ok(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Inventory>> getInventoryById(@PathVariable Long id) {
        Inventory item = inventoryService.getInventoryById(id);
        return ResponseEntity.ok(ApiResponse.ok(item));
    }

    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<ApiResponse<List<Inventory>>> getInventoryByWarehouse(@PathVariable Long warehouseId) {
        List<Inventory> list = inventoryService.getInventoryByWarehouse(warehouseId);
        return ResponseEntity.ok(ApiResponse.ok(list));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<List<Inventory>>> getInventoryByProduct(@PathVariable Long productId) {
        List<Inventory> list = inventoryService.getInventoryByProduct(productId);
        return ResponseEntity.ok(ApiResponse.ok(list));
    }
}
