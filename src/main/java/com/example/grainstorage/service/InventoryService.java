package com.example.grainstorage.service;

import com.example.grainstorage.entity.GrainLot;
import com.example.grainstorage.entity.Inventory;
import com.example.grainstorage.entity.Product;
import com.example.grainstorage.entity.Warehouse;
import com.example.grainstorage.entity.enums.GrainGrade;
import com.example.grainstorage.exception.InsufficientInventoryException;
import com.example.grainstorage.exception.ResourceNotFoundException;
import com.example.grainstorage.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Inventory getInventoryById(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory record not found with id: " + id));
    }

    public List<Inventory> getInventoryByWarehouse(Long warehouseId) {
        return inventoryRepository.findByWarehouseId(warehouseId);
    }

    public List<Inventory> getInventoryByProduct(Long productId) {
        return inventoryRepository.findByProductId(productId);
    }

    public Double getTotalQuantityInWarehouse(Long warehouseId) {
        return inventoryRepository.sumQuantityByWarehouse(warehouseId);
    }

    public Double getAvailableQuantityByWarehouseAndProduct(Long warehouseId, Long productId) {
        return inventoryRepository.sumQuantityByWarehouseAndProduct(warehouseId, productId);
    }

    @Transactional
    public Inventory addInventory(Warehouse warehouse, Product product, GrainLot grainLot, Double quantity, GrainGrade grade) {
        Inventory inventory = new Inventory(warehouse, product, grainLot, quantity, grade);
        return inventoryRepository.save(inventory);
    }

    @Transactional
    public void reduceInventory(Long warehouseId, Long productId, Double quantityToDeduct) {
        Double available = getAvailableQuantityByWarehouseAndProduct(warehouseId, productId);
        if (available < quantityToDeduct) {
            throw new InsufficientInventoryException(
                    String.format("Insufficient inventory in warehouse! Available: %.2f kg, Requested: %.2f kg", available, quantityToDeduct)
            );
        }

        // Deduct FIFO from matching warehouse inventory lines
        List<Inventory> stockLines = inventoryRepository.findByWarehouseIdAndProductId(warehouseId, productId);
        double remainingToDeduct = quantityToDeduct;

        for (Inventory item : stockLines) {
            if (remainingToDeduct <= 0) {
                break;
            }
            if (item.getQuantity() <= remainingToDeduct) {
                remainingToDeduct -= item.getQuantity();
                item.setQuantity(0.0);
                item.setLastUpdated(LocalDateTime.now());
                inventoryRepository.delete(item); // Remove depleted lot
            } else {
                item.setQuantity(item.getQuantity() - remainingToDeduct);
                item.setLastUpdated(LocalDateTime.now());
                remainingToDeduct = 0.0;
                inventoryRepository.save(item);
            }
        }
    }
}
