package com.example.grainstorage.repository;

import com.example.grainstorage.entity.SalesOrder;
import com.example.grainstorage.entity.enums.SalesOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
    Optional<SalesOrder> findBySalesOrderNumber(String salesOrderNumber);
    List<SalesOrder> findByCustomerId(Long customerId);
    List<SalesOrder> findByWarehouseId(Long warehouseId);
    List<SalesOrder> findByStatus(SalesOrderStatus status);
}
