package com.example.grainstorage.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "budget_name", nullable = false, length = 150)
    private String budgetName;

    @Column(name = "financial_year", nullable = false, length = 30)
    private String financialYear;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "planned_amount", nullable = false)
    private Double plannedAmount;

    @Column(name = "actual_amount", nullable = false)
    private Double actualAmount = 0.0;

    @Column(name = "variance", nullable = false)
    private Double variance;

    public Budget() {
    }

    public Budget(String budgetName, String financialYear, Warehouse warehouse, Account account, Double plannedAmount) {
        this.budgetName = budgetName;
        this.financialYear = financialYear;
        this.warehouse = warehouse;
        this.account = account;
        this.plannedAmount = plannedAmount;
        this.actualAmount = 0.0;
        this.variance = plannedAmount;
    }

    @PrePersist
    @PreUpdate
    protected void onPersistOrUpdate() {
        if (this.actualAmount == null) {
            this.actualAmount = 0.0;
        }
        if (this.plannedAmount != null) {
            this.variance = this.plannedAmount - this.actualAmount;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Double getPlannedAmount() {
        return plannedAmount;
    }

    public void setPlannedAmount(Double plannedAmount) {
        this.plannedAmount = plannedAmount;
        if (this.actualAmount != null) {
            this.variance = plannedAmount - this.actualAmount;
        }
    }

    public Double getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Double actualAmount) {
        this.actualAmount = actualAmount;
        if (this.plannedAmount != null) {
            this.variance = this.plannedAmount - actualAmount;
        }
    }

    public Double getVariance() {
        return variance;
    }

    public void setVariance(Double variance) {
        this.variance = variance;
    }
}
