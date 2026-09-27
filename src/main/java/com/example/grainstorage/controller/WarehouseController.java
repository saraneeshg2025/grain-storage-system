package com.example.grainstorage.controller;

import com.example.grainstorage.dto.request.WarehouseRequest;
import com.example.grainstorage.dto.response.ApiResponse;
import com.example.grainstorage.dto.response.WarehouseCapacityResponse;
import com.example.grainstorage.entity.Warehouse;
import com.example.grainstorage.service.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
@CrossOrigin(origins = "*")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Warehouse>> createWarehouse(@Valid @RequestBody WarehouseRequest request) {
        Warehouse warehouse = warehouseService.createWarehouse(request);
        return new ResponseEntity<>(ApiResponse.ok("Warehouse created successfully", warehouse), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Warehouse>>> getAllWarehouses() {
        List<Warehouse> list = warehouseService.getAllWarehouses();
        return ResponseEntity.ok(ApiResponse.ok(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Warehouse>> getWarehouseById(@PathVariable Long id) {
        Warehouse warehouse = warehouseService.getWarehouseById(id);
        return ResponseEntity.ok(ApiResponse.ok(warehouse));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Warehouse>> updateWarehouse(@PathVariable Long id, @Valid @RequestBody WarehouseRequest request) {
        Warehouse updated = warehouseService.updateWarehouse(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Warehouse updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteWarehouse(@PathVariable Long id) {
        warehouseService.deleteWarehouse(id);
        return ResponseEntity.ok(ApiResponse.ok("Warehouse deleted successfully", null));
    }

    @GetMapping("/{id}/capacity")
    public ResponseEntity<ApiResponse<WarehouseCapacityResponse>> getWarehouseCapacity(@PathVariable Long id) {
        WarehouseCapacityResponse capacity = warehouseService.getWarehouseCapacity(id);
        return ResponseEntity.ok(ApiResponse.ok(capacity));
    }
}
