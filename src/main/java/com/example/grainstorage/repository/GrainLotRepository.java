package com.example.grainstorage.repository;

import com.example.grainstorage.entity.GrainLot;
import com.example.grainstorage.entity.enums.LotStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GrainLotRepository extends JpaRepository<GrainLot, Long> {
    Optional<GrainLot> findByLotNumber(String lotNumber);
    List<GrainLot> findByWarehouseId(Long warehouseId);
    List<GrainLot> findByProductId(Long productId);
    List<GrainLot> findByStatus(LotStatus status);
}
