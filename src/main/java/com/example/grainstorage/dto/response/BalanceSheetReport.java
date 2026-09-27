package com.example.grainstorage.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BalanceSheetReport {
    private LocalDateTime generatedAt;
    private List<AccountBalanceItem> assets = new ArrayList<>();
    private List<AccountBalanceItem> liabilities = new ArrayList<>();
    private Double totalAssets = 0.0;
    private Double totalLiabilities = 0.0;
    private Double netWorth = 0.0;

    public BalanceSheetReport() {
        this.generatedAt = LocalDateTime.now();
    }

    public static class AccountBalanceItem {
        private String accountCode;
        private String accountName;
        private Double balance;

        public AccountBalanceItem() {
        }

        public AccountBalanceItem(String accountCode, String accountName, Double balance) {
            this.accountCode = accountCode;
            this.accountName = accountName;
            this.balance = balance != null ? balance : 0.0;
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

        public Double getBalance() {
            return balance;
        }

        public void setBalance(Double balance) {
            this.balance = balance;
        }
    }

    public void addAsset(String code, String name, Double balance) {
        this.assets.add(new AccountBalanceItem(code, name, balance));
        this.totalAssets += (balance != null ? balance : 0.0);
        calculateNetWorth();
    }

    public void addLiability(String code, String name, Double balance) {
        this.liabilities.add(new AccountBalanceItem(code, name, balance));
        this.totalLiabilities += (balance != null ? balance : 0.0);
        calculateNetWorth();
    }

    private void calculateNetWorth() {
        this.netWorth = this.totalAssets - this.totalLiabilities;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    public List<AccountBalanceItem> getAssets() {
        return assets;
    }

    public void setAssets(List<AccountBalanceItem> assets) {
        this.assets = assets;
    }

    public List<AccountBalanceItem> getLiabilities() {
        return liabilities;
    }

    public void setLiabilities(List<AccountBalanceItem> liabilities) {
        this.liabilities = liabilities;
    }

    public Double getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(Double totalAssets) {
        this.totalAssets = totalAssets;
    }

    public Double getTotalLiabilities() {
        return totalLiabilities;
    }

    public void setTotalLiabilities(Double totalLiabilities) {
        this.totalLiabilities = totalLiabilities;
    }

    public Double getNetWorth() {
        return netWorth;
    }

    public void setNetWorth(Double netWorth) {
        this.netWorth = netWorth;
    }
}
