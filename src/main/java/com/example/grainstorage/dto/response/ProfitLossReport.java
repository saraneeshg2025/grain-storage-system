package com.example.grainstorage.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProfitLossReport {
    private LocalDateTime generatedAt;
    private List<IncomeExpenseItem> incomeList = new ArrayList<>();
    private List<IncomeExpenseItem> expenseList = new ArrayList<>();
    private Double totalIncome = 0.0;
    private Double totalExpenses = 0.0;
    private Double netProfitOrLoss = 0.0;

    public ProfitLossReport() {
        this.generatedAt = LocalDateTime.now();
    }

    public static class IncomeExpenseItem {
        private String accountCode;
        private String accountName;
        private Double amount;

        public IncomeExpenseItem() {
        }

        public IncomeExpenseItem(String accountCode, String accountName, Double amount) {
            this.accountCode = accountCode;
            this.accountName = accountName;
            this.amount = amount != null ? amount : 0.0;
        }

        public String getAccountCode() {
            return accountCode;
        }

        public void setAccountCode(String accountCode) {
            this.accountCode = accountCode;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }
    }

    public void addIncome(String code, String name, Double amount) {
        this.incomeList.add(new IncomeExpenseItem(code, name, amount));
        this.totalIncome += (amount != null ? amount : 0.0);
        calculateNet();
    }

    public void addExpense(String code, String name, Double amount) {
        this.expenseList.add(new IncomeExpenseItem(code, name, amount));
        this.totalExpenses += (amount != null ? amount : 0.0);
        calculateNet();
    }

    private void calculateNet() {
        this.netProfitOrLoss = this.totalIncome - this.totalExpenses;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    public List<IncomeExpenseItem> getIncomeList() {
        return incomeList;
    }

    public void setIncomeList(List<IncomeExpenseItem> incomeList) {
        this.incomeList = incomeList;
    }

    public List<IncomeExpenseItem> getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(List<IncomeExpenseItem> expenseList) {
        this.expenseList = expenseList;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public Double getNetProfitOrLoss() {
        return netProfitOrLoss;
    }

    public void setNetProfitOrLoss(Double netProfitOrLoss) {
        this.netProfitOrLoss = netProfitOrLoss;
    }
}
