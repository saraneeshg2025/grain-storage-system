package com.example.grainstorage.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BudgetVarianceReport {
    private LocalDateTime generatedAt;
    private Double totalPlanned = 0.0;
    private Double totalActual = 0.0;
    private Double totalVariance = 0.0;
    private List<BudgetItemDetail> budgets = new ArrayList<>();

    public BudgetVarianceReport() {
        this.generatedAt = LocalDateTime.now();
    }

    public static class BudgetItemDetail {
        private Long budgetId;
        private String budgetName;
        private String financialYear;
        private String warehouseName;
        private String accountName;
        private Double plannedAmount;
        private Double actualAmount;
        private Double variance;
        private Double variancePercentage;

        public BudgetItemDetail() {
        }

        public BudgetItemDetail(Long budgetId, String budgetName, String financialYear, String warehouseName,
                                String accountName, Double plannedAmount, Double actualAmount, Double variance) {
            this.budgetId = budgetId;
            this.budgetName = budgetName;
            this.financialYear = financialYear;
            this.warehouseName = warehouseName;
            this.accountName = accountName;
            this.plannedAmount = plannedAmount != null ? plannedAmount : 0.0;
            this.actualAmount = actualAmount != null ? actualAmount : 0.0;
            this.variance = variance != null ? variance : (this.plannedAmount - this.actualAmount);
            this.variancePercentage = (this.plannedAmount > 0)
                    ? Math.round(((this.variance / this.plannedAmount) * 100.0) * 100.0) / 100.0
                    : 0.0;
        }

        public Long getBudgetId() {
            return budgetId;
        }

        public void setBudgetId(Long budgetId) {
            this.budgetId = budgetId;
        }

        public String getBudgetName() {
            return budgetName;
        }

        public void setBudgetName(String budgetName) {
            this.budgetName = budgetName;
        }

        public String getFinancialYear() {
            return financialYear;
        }

        public void setFinancialYear(String financialYear) {
            this.financialYear = financialYear;
        }

        public String getWarehouseName() {
            return warehouseName;
        }

        public void setWarehouseName(String warehouseName) {
            this.warehouseName = warehouseName;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public Double getPlannedAmount() {
            return plannedAmount;
        }

        public void setPlannedAmount(Double plannedAmount) {
            this.plannedAmount = plannedAmount;
        }

        public Double getActualAmount() {
            return actualAmount;
        }

        public void setActualAmount(Double actualAmount) {
            this.actualAmount = actualAmount;
        }

        public Double getVariance() {
            return variance;
        }

        public void setVariance(Double variance) {
            this.variance = variance;
        }

        public Double getVariancePercentage() {
            return variancePercentage;
        }

        public void setVariancePercentage(Double variancePercentage) {
            this.variancePercentage = variancePercentage;
        }
    }

    public void addBudgetItem(BudgetItemDetail item) {
        this.budgets.add(item);
        this.totalPlanned += item.getPlannedAmount();
        this.totalActual += item.getActualAmount();
        this.totalVariance = this.totalPlanned - this.totalActual;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    public Double getTotalPlanned() {
        return totalPlanned;
    }

    public void setTotalPlanned(Double totalPlanned) {
        this.totalPlanned = totalPlanned;
    }

    public Double getTotalActual() {
        return totalActual;
    }

    public void setTotalActual(Double totalActual) {
        this.totalActual = totalActual;
    }

    public Double getTotalVariance() {
        return totalVariance;
    }

    public void setTotalVariance(Double totalVariance) {
        this.totalVariance = totalVariance;
    }

    public List<BudgetItemDetail> getBudgets() {
        return budgets;
    }

    public void setBudgets(List<BudgetItemDetail> budgets) {
        this.budgets = budgets;
    }
}
