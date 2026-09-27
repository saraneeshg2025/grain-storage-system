package com.example.grainstorage.repository;

import com.example.grainstorage.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByWarehouseId(Long warehouseId);
    List<Budget> findByFinancialYear(String financialYear);
    List<Budget> findByWarehouseIdAndFinancialYear(Long warehouseId, String financialYear);
    List<Budget> findByAccountId(Long accountId);
}
