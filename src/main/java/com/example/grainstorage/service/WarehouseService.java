package com.example.grainstorage.service;

import com.example.grainstorage.dto.request.WarehouseRequest;
import com.example.grainstorage.dto.response.WarehouseCapacityResponse;
import com.example.grainstorage.entity.Warehouse;
import com.example.grainstorage.entity.enums.WarehouseStatus;
import com.example.grainstorage.exception.InsufficientCapacityException;
import com.example.grainstorage.exception.ResourceNotFoundException;
import com.example.grainstorage.repository.WarehouseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Transactional
    public Warehouse createWarehouse(WarehouseRequest request) {
        if (warehouseRepository.findByWarehouseCode(request.getWarehouseCode()).isPresent()) {
            throw new IllegalArgumentException("Warehouse code already exists: " + request.getWarehouseCode());
        }
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseCode(request.getWarehouseCode());
        warehouse.setWarehouseName(request.getWarehouseName());
        warehouse.setLocation(request.getLocation());
        warehouse.setTotalCapacity(request.getTotalCapacity());
        warehouse.setUsedCapacity(0.0);
        warehouse.setAvailableCapacity(request.getTotalCapacity());
        warehouse.setStatus(request.getStatus() != null ? request.getStatus() : WarehouseStatus.ACTIVE);
        return warehouseRepository.save(warehouse);
    }

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    public Warehouse getWarehouseById(Long id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with id: " + id));
    }

    @Transactional
    public Warehouse updateWarehouse(Long id, WarehouseRequest request) {
        Warehouse warehouse = getWarehouseById(id);
        warehouse.setWarehouseName(request.getWarehouseName());
        warehouse.setLocation(request.getLocation());
        warehouse.setTotalCapacity(request.getTotalCapacity());
        if (request.getStatus() != null) {
            warehouse.setStatus(request.getStatus());
        }
        warehouse.setAvailableCapacity(Math.max(0.0, warehouse.getTotalCapacity() - warehouse.getUsedCapacity()));
        return warehouseRepository.save(warehouse);
    }

    @Transactional
    public void deleteWarehouse(Long id) {
        Warehouse warehouse = getWarehouseById(id);
        warehouseRepository.delete(warehouse);
    }

    public WarehouseCapacityResponse getWarehouseCapacity(Long id) {
        Warehouse warehouse = getWarehouseById(id);
        return new WarehouseCapacityResponse(
                warehouse.getId(),
                warehouse.getWarehouseCode(),
                warehouse.getWarehouseName(),
                warehouse.getLocation(),
                warehouse.getTotalCapacity(),
                warehouse.getUsedCapacity(),
                warehouse.getAvailableCapacity(),
                warehouse.getStatus()
        );
    }

    public void validateCapacity(Long warehouseId, Double incomingQuantity) {
        Warehouse warehouse = getWarehouseById(warehouseId);
        if (warehouse.getStatus() != WarehouseStatus.ACTIVE) {
            throw new InsufficientCapacityException("Warehouse is not active: " + warehouse.getWarehouseName());
        }
        if (incomingQuantity > warehouse.getAvailableCapacity()) {
            throw new InsufficientCapacityException("Insufficient warehouse capacity");
        }
    }

    @Transactional
    public void increaseUsedCapacity(Long warehouseId, Double quantity) {
        Warehouse warehouse = getWarehouseById(warehouseId);
        if (quantity > warehouse.getAvailableCapacity()) {
            throw new InsufficientCapacityException("Insufficient warehouse capacity");
        }
        double newUsed = warehouse.getUsedCapacity() + quantity;
        warehouse.setUsedCapacity(newUsed);
        warehouse.setAvailableCapacity(Math.max(0.0, warehouse.getTotalCapacity() - newUsed));
        warehouseRepository.save(warehouse);
    }

    @Transactional
    public void decreaseUsedCapacity(Long warehouseId, Double quantity) {
        Warehouse warehouse = getWarehouseById(warehouseId);
        double newUsed = Math.max(0.0, warehouse.getUsedCapacity() - quantity);
        warehouse.setUsedCapacity(newUsed);
        warehouse.setAvailableCapacity(Math.max(0.0, warehouse.getTotalCapacity() - newUsed));
        warehouseRepository.save(warehouse);
    }
}
