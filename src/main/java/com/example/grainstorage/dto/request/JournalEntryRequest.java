package com.example.grainstorage.dto.request;

import jakarta.validation.constraints.NotNull;

public class JournalEntryRequest {

    @NotNull(message = "Account ID is required")
    private Long accountId;

    private Double debit = 0.0;

    private Double credit = 0.0;

    private String description;

    public JournalEntryRequest() {
    }

    public JournalEntryRequest(Long accountId, Double debit, Double credit, String description) {
        this.accountId = accountId;
        this.debit = debit != null ? debit : 0.0;
        this.credit = credit != null ? credit : 0.0;
        this.description = description;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Double getDebit() {
        return debit;
    }

    public void setDebit(Double debit) {
        this.debit = debit;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
