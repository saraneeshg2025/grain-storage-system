package com.example.grainstorage.service;

import com.example.grainstorage.dto.response.*;
import com.example.grainstorage.entity.Account;
import com.example.grainstorage.entity.Inventory;
import com.example.grainstorage.entity.Warehouse;
import com.example.grainstorage.entity.enums.AccountType;
import com.example.grainstorage.repository.AccountRepository;
import com.example.grainstorage.repository.InventoryRepository;
import com.example.grainstorage.repository.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private final AccountRepository accountRepository;
    private final WarehouseRepository warehouseRepository;
    private final InventoryRepository inventoryRepository;
    private final BudgetService budgetService;
    private final WarehouseService warehouseService;

    public ReportService(AccountRepository accountRepository,
                         WarehouseRepository warehouseRepository,
                         InventoryRepository inventoryRepository,
                         BudgetService budgetService,
                         WarehouseService warehouseService) {
        this.accountRepository = accountRepository;
        this.warehouseRepository = warehouseRepository;
        this.inventoryRepository = inventoryRepository;
        this.budgetService = budgetService;
        this.warehouseService = warehouseService;
    }

    public BalanceSheetReport generateBalanceSheet() {
        BalanceSheetReport report = new BalanceSheetReport();

        List<Account> assetAccounts = accountRepository.findByAccountType(AccountType.ASSET);
        for (Account a : assetAccounts) {
            report.addAsset(a.getAccountCode(), a.getAccountName(), a.getCurrentBalance());
        }

        List<Account> liabilityAccounts = accountRepository.findByAccountType(AccountType.LIABILITY);
        for (Account l : liabilityAccounts) {
            report.addLiability(l.getAccountCode(), l.getAccountName(), l.getCurrentBalance());
        }

        return report;
    }

    public ProfitLossReport generateProfitLossReport() {
        ProfitLossReport report = new ProfitLossReport();

        List<Account> incomeAccounts = accountRepository.findByAccountType(AccountType.INCOME);
        for (Account inc : incomeAccounts) {
            report.addIncome(inc.getAccountCode(), inc.getAccountName(), inc.getCurrentBalance());
        }

        List<Account> expenseAccounts = accountRepository.findByAccountType(AccountType.EXPENSE);
        for (Account exp : expenseAccounts) {
            report.addExpense(exp.getAccountCode(), exp.getAccountName(), exp.getCurrentBalance());
        }

        return report;
    }

    public BudgetVarianceReport generateBudgetReport() {
        return budgetService.getBudgetVarianceReport();
    }

    public WarehouseCapacityReport generateWarehouseCapacityReport() {
        WarehouseCapacityReport report = new WarehouseCapacityReport();
        List<Warehouse> warehouses = warehouseRepository.findAll();

        for (Warehouse wh : warehouses) {
            WarehouseCapacityResponse response = warehouseService.getWarehouseCapacity(wh.getId());
            report.addWarehouse(response);
        }

        return report;
    }

    public InventoryReport generateInventoryReport() {
        InventoryReport report = new InventoryReport();
        List<Inventory> list = inventoryRepository.findAll();

        for (Inventory inv : list) {
            InventoryReport.InventoryItemDetail item = new InventoryReport.InventoryItemDetail(
                    inv.getId(),
                    inv.getWarehouse() != null ? inv.getWarehouse().getId() : null,
                    inv.getWarehouse() != null ? inv.getWarehouse().getWarehouseName() : "N/A",
                    inv.getProduct() != null ? inv.getProduct().getId() : null,
                    inv.getProduct() != null ? inv.getProduct().getProductName() : "N/A",
                    (inv.getProduct() != null && inv.getProduct().getGrainType() != null) ? inv.getProduct().getGrainType().name() : "N/A",
                    inv.getGrainLot() != null ? inv.getGrainLot().getLotNumber() : "N/A",
                    inv.getQuantity(),
                    inv.getProduct() != null ? inv.getProduct().getUnit() : "KG",
                    inv.getGrade(),
                    inv.getLastUpdated()
            );
            report.addItem(item);
        }

        return report;
    }
}
