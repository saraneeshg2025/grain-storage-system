package com.example.grainstorage.repository;

import com.example.grainstorage.entity.SiloTelemetry;
import com.example.grainstorage.entity.enums.SpoilageRiskLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SiloTelemetryRepository extends JpaRepository<SiloTelemetry, Long> {
    List<SiloTelemetry> findByWarehouseIdOrderByRecordedAtDesc(Long warehouseId);
    Optional<SiloTelemetry> findTopByWarehouseIdOrderByRecordedAtDesc(Long warehouseId);
    List<SiloTelemetry> findByRiskLevelNot(SpoilageRiskLevel riskLevel);
}
