package com.example.grainstorage.repository;

import com.example.grainstorage.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByWarehouseId(Long warehouseId);
    List<Inventory> findByProductId(Long productId);
    Optional<Inventory> findByGrainLotId(Long grainLotId);
    List<Inventory> findByWarehouseIdAndProductId(Long warehouseId, Long productId);

    @Query("SELECT COALESCE(SUM(i.quantity), 0.0) FROM Inventory i WHERE i.warehouse.id = :warehouseId AND i.product.id = :productId")
    Double sumQuantityByWarehouseAndProduct(@Param("warehouseId") Long warehouseId, @Param("productId") Long productId);

    @Query("SELECT COALESCE(SUM(i.quantity), 0.0) FROM Inventory i WHERE i.warehouse.id = :warehouseId")
    Double sumQuantityByWarehouse(@Param("warehouseId") Long warehouseId);
}
