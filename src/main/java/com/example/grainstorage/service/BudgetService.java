package com.example.grainstorage.service;

import com.example.grainstorage.dto.request.BudgetRequest;
import com.example.grainstorage.dto.response.BudgetVarianceReport;
import com.example.grainstorage.entity.Account;
import com.example.grainstorage.entity.Budget;
import com.example.grainstorage.entity.Warehouse;
import com.example.grainstorage.exception.ResourceNotFoundException;
import com.example.grainstorage.repository.BudgetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final WarehouseService warehouseService;
    private final AccountService accountService;

    public BudgetService(BudgetRepository budgetRepository,
                         WarehouseService warehouseService,
                         AccountService accountService) {
        this.budgetRepository = budgetRepository;
        this.warehouseService = warehouseService;
        this.accountService = accountService;
    }

    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    public Budget getBudgetById(Long id) {
        return budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found with id: " + id));
    }

    @Transactional
    public Budget createBudget(BudgetRequest request) {
        Warehouse warehouse = warehouseService.getWarehouseById(request.getWarehouseId());
        Account account = accountService.getAccountById(request.getAccountId());

        Budget budget = new Budget(
                request.getBudgetName(),
                request.getFinancialYear(),
                warehouse,
                account,
                request.getPlannedAmount()
        );

        return budgetRepository.save(budget);
    }

    @Transactional
    public Budget updateActualExpenditure(Long id, Double additionalActual) {
        Budget budget = getBudgetById(id);
        double newActual = budget.getActualAmount() + additionalActual;
        budget.setActualAmount(newActual);
        budget.setVariance(budget.getPlannedAmount() - newActual);
        return budgetRepository.save(budget);
    }

    public BudgetVarianceReport getBudgetVarianceReport() {
        List<Budget> budgets = budgetRepository.findAll();
        BudgetVarianceReport report = new BudgetVarianceReport();

        for (Budget b : budgets) {
            BudgetVarianceReport.BudgetItemDetail item = new BudgetVarianceReport.BudgetItemDetail(
                    b.getId(),
                    b.getBudgetName(),
                    b.getFinancialYear(),
                    b.getWarehouse() != null ? b.getWarehouse().getWarehouseName() : "N/A",
                    b.getAccount() != null ? b.getAccount().getAccountName() : "N/A",
                    b.getPlannedAmount(),
                    b.getActualAmount(),
                    b.getVariance()
            );
            report.addBudgetItem(item);
        }

        return report;
    }
}
