package com.example.grainstorage.dto.request;

import com.example.grainstorage.entity.enums.JournalType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class JournalRequest {

    private String journalNumber;

    @NotNull(message = "Journal type is required (PURCHASE, SALES, PAYMENT, RECEIPT, INVENTORY, EXPENSE)")
    private JournalType journalType;

    private LocalDateTime transactionDate;

    private String description;

    private String referenceNumber;

    @NotEmpty(message = "Journal must have at least two entries")
    @Valid
    private List<JournalEntryRequest> entries;

    public JournalRequest() {
    }

    public String getJournalNumber() {
        return journalNumber;
    }

    public void setJournalNumber(String journalNumber) {
        this.journalNumber = journalNumber;
    }

    public JournalType getJournalType() {
        return journalType;
    }

    public void setJournalType(JournalType journalType) {
        this.journalType = journalType;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public List<JournalEntryRequest> getEntries() {
        return entries;
    }

    public void setEntries(List<JournalEntryRequest> entries) {
        this.entries = entries;
    }
}
