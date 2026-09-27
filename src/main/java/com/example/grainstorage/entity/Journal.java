package com.example.grainstorage.entity;

import com.example.grainstorage.entity.enums.JournalType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "journals")
public class Journal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "journal_number", nullable = false, unique = true, length = 50)
    private String journalNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "journal_type", nullable = false, length = 50)
    private JournalType journalType;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "reference_number", length = 100)
    private String referenceNumber;

    @Column(name = "total_debit", nullable = false)
    private Double totalDebit = 0.0;

    @Column(name = "total_credit", nullable = false)
    private Double totalCredit = 0.0;

    @OneToMany(mappedBy = "journal", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<JournalEntry> entries = new ArrayList<>();

    public Journal() {
    }

    public Journal(String journalNumber, JournalType journalType, LocalDateTime transactionDate, String description, String referenceNumber) {
        this.journalNumber = journalNumber;
        this.journalType = journalType;
        this.transactionDate = transactionDate != null ? transactionDate : LocalDateTime.now();
        this.description = description;
        this.referenceNumber = referenceNumber;
    }

    public void addEntry(JournalEntry entry) {
        entries.add(entry);
        entry.setJournal(this);
        recalculateTotals();
    }

    public void recalculateTotals() {
        double dSum = 0.0;
        double cSum = 0.0;
        for (JournalEntry entry : entries) {
            if (entry.getDebit() != null) {
                dSum += entry.getDebit();
            }
            if (entry.getCredit() != null) {
                cSum += entry.getCredit();
            }
        }
        this.totalDebit = dSum;
        this.totalCredit = cSum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getTotalDebit() {
        return totalDebit;
    }

    public void setTotalDebit(Double totalDebit) {
        this.totalDebit = totalDebit;
    }

    public Double getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(Double totalCredit) {
        this.totalCredit = totalCredit;
    }

    public List<JournalEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<JournalEntry> entries) {
        this.entries = entries;
        recalculateTotals();
    }
}
