package com.example.grainstorage.service;

import com.example.grainstorage.dto.request.JournalEntryRequest;
import com.example.grainstorage.dto.request.JournalRequest;
import com.example.grainstorage.entity.Account;
import com.example.grainstorage.entity.Journal;
import com.example.grainstorage.entity.JournalEntry;
import com.example.grainstorage.entity.enums.AccountType;
import com.example.grainstorage.entity.enums.JournalType;
import com.example.grainstorage.exception.InvalidTransactionException;
import com.example.grainstorage.exception.ResourceNotFoundException;
import com.example.grainstorage.repository.JournalEntryRepository;
import com.example.grainstorage.repository.JournalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AccountingService {

    private final JournalRepository journalRepository;
    private final JournalEntryRepository journalEntryRepository;
    private final AccountService accountService;

    public AccountingService(JournalRepository journalRepository,
                             JournalEntryRepository journalEntryRepository,
                             AccountService accountService) {
        this.journalRepository = journalRepository;
        this.journalEntryRepository = journalEntryRepository;
        this.accountService = accountService;
    }

    public List<Journal> getAllJournals() {
        return journalRepository.findAll();
    }

    public List<Journal> getJournalsByType(JournalType type) {
        return journalRepository.findByJournalType(type);
    }

    public Journal getJournalById(Long id) {
        return journalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Journal not found with id: " + id));
    }

    @Transactional
    public Journal createJournal(JournalRequest request) {
        if (request.getEntries() == null || request.getEntries().size() < 2) {
            throw new InvalidTransactionException("A journal entry requires at least two lines for double-entry bookkeeping");
        }

        String journalNumber = request.getJournalNumber();
        if (journalNumber == null || journalNumber.isBlank()) {
            journalNumber = "JRNL-" + System.currentTimeMillis();
        }

        Journal journal = new Journal(
                journalNumber,
                request.getJournalType(),
                request.getTransactionDate() != null ? request.getTransactionDate() : LocalDateTime.now(),
                request.getDescription(),
                request.getReferenceNumber()
        );

        double totalDebit = 0.0;
        double totalCredit = 0.0;

        for (JournalEntryRequest entryReq : request.getEntries()) {
            Account account = accountService.getAccountById(entryReq.getAccountId());
            double debit = entryReq.getDebit() != null ? entryReq.getDebit() : 0.0;
            double credit = entryReq.getCredit() != null ? entryReq.getCredit() : 0.0;

            if (debit <= 0 && credit <= 0) {
                throw new InvalidTransactionException("Each journal line must specify either a debit or credit amount greater than zero");
            }
            if (debit > 0 && credit > 0) {
                throw new InvalidTransactionException("A single journal line cannot have both debit and credit amounts");
            }

            JournalEntry entry = new JournalEntry(journal, account, debit, credit, entryReq.getDescription());
            journal.addEntry(entry);

            totalDebit += debit;
            totalCredit += credit;

            // Update account balance
            if (debit > 0) {
                accountService.updateAccountBalance(account, debit, true);
            } else {
                accountService.updateAccountBalance(account, credit, false);
            }
        }

        // Validate double-entry equality (with small epsilon for floating-point safety)
        if (Math.abs(totalDebit - totalCredit) > 0.001) {
            throw new InvalidTransactionException(
                    String.format("Unbalanced journal! Total Debit (%.2f) must equal Total Credit (%.2f)", totalDebit, totalCredit)
            );
        }

        return journalRepository.save(journal);
    }

    @Transactional
    public Journal recordPurchaseJournal(String poNumber, Double amount, String description) {
        Account inventoryAcc = accountService.getOrCreateAccount("1001", "Grain Stock Inventory", AccountType.ASSET, 0.0);
        Account creditorAcc = accountService.getOrCreateAccount("2001", "Farmer Creditors", AccountType.LIABILITY, 0.0);

        Journal journal = new Journal(
                "JRNL-PUR-" + System.currentTimeMillis(),
                JournalType.PURCHASE,
                LocalDateTime.now(),
                description != null ? description : "Grain procurement for PO: " + poNumber,
                poNumber
        );

        JournalEntry debitEntry = new JournalEntry(journal, inventoryAcc, amount, 0.0, "Debit: Grain Inventory addition");
        JournalEntry creditEntry = new JournalEntry(journal, creditorAcc, 0.0, amount, "Credit: Farmer Creditor liability");

        journal.addEntry(debitEntry);
        journal.addEntry(creditEntry);

        accountService.updateAccountBalance(inventoryAcc, amount, true);
        accountService.updateAccountBalance(creditorAcc, amount, false);

        return journalRepository.save(journal);
    }

    @Transactional
    public Journal recordFarmerPaymentJournal(String paymentNumber, Double amount, String referenceNumber, boolean isBank) {
        Account creditorAcc = accountService.getOrCreateAccount("2001", "Farmer Creditors", AccountType.LIABILITY, 0.0);
        Account payAcc = isBank
                ? accountService.getOrCreateAccount("1002", "Bank", AccountType.ASSET, 1000000.0)
                : accountService.getOrCreateAccount("1003", "Cash", AccountType.ASSET, 100000.0);

        Journal journal = new Journal(
                "JRNL-PAY-" + System.currentTimeMillis(),
                JournalType.PAYMENT,
                LocalDateTime.now(),
                "Farmer payment for: " + paymentNumber + " ref: " + referenceNumber,
                referenceNumber
        );

        JournalEntry debitEntry = new JournalEntry(journal, creditorAcc, amount, 0.0, "Debit: Farmer Creditor reduction");
        JournalEntry creditEntry = new JournalEntry(journal, payAcc, 0.0, amount, "Credit: " + payAcc.getAccountName() + " disbursement");

        journal.addEntry(debitEntry);
        journal.addEntry(creditEntry);

        accountService.updateAccountBalance(creditorAcc, amount, true);
        accountService.updateAccountBalance(payAcc, amount, false);

        return journalRepository.save(journal);
    }

    @Transactional
    public Journal recordSalesJournal(String invoiceNumber, Double amount, String description) {
        Account receivableAcc = accountService.getOrCreateAccount("1004", "Customer Receivable", AccountType.ASSET, 0.0);
        Account salesIncomeAcc = accountService.getOrCreateAccount("3001", "PDS Distribution Sales", AccountType.INCOME, 0.0);

        Journal journal = new Journal(
                "JRNL-SAL-" + System.currentTimeMillis(),
                JournalType.SALES,
                LocalDateTime.now(),
                description != null ? description : "PDS Grain Distribution invoice: " + invoiceNumber,
                invoiceNumber
        );

        JournalEntry debitEntry = new JournalEntry(journal, receivableAcc, amount, 0.0, "Debit: Customer Receivable");
        JournalEntry creditEntry = new JournalEntry(journal, salesIncomeAcc, 0.0, amount, "Credit: PDS Distribution Sales Income");

        journal.addEntry(debitEntry);
        journal.addEntry(creditEntry);

        accountService.updateAccountBalance(receivableAcc, amount, true);
        accountService.updateAccountBalance(salesIncomeAcc, amount, false);

        return journalRepository.save(journal);
    }

    @Transactional
    public Journal recordCustomerReceiptJournal(String paymentNumber, Double amount, String referenceNumber, boolean isBank) {
        Account payAcc = isBank
                ? accountService.getOrCreateAccount("1002", "Bank", AccountType.ASSET, 1000000.0)
                : accountService.getOrCreateAccount("1003", "Cash", AccountType.ASSET, 100000.0);
        Account receivableAcc = accountService.getOrCreateAccount("1004", "Customer Receivable", AccountType.ASSET, 0.0);

        Journal journal = new Journal(
                "JRNL-REC-" + System.currentTimeMillis(),
                JournalType.RECEIPT,
                LocalDateTime.now(),
                "Payment received from PDS agency: " + paymentNumber + " ref: " + referenceNumber,
                referenceNumber
        );

        JournalEntry debitEntry = new JournalEntry(journal, payAcc, amount, 0.0, "Debit: " + payAcc.getAccountName() + " receipt");
        JournalEntry creditEntry = new JournalEntry(journal, receivableAcc, 0.0, amount, "Credit: Customer Receivable settled");

        journal.addEntry(debitEntry);
        journal.addEntry(creditEntry);

        accountService.updateAccountBalance(payAcc, amount, true);
        accountService.updateAccountBalance(receivableAcc, amount, false);

        return journalRepository.save(journal);
    }

    @Transactional
    public Journal recordExpenseJournal(String expenseAccountName, Double amount, String description, boolean isBank) {
        Account expenseAcc = accountService.getOrCreateAccount("4002", expenseAccountName, AccountType.EXPENSE, 0.0);
        Account payAcc = isBank
                ? accountService.getOrCreateAccount("1002", "Bank", AccountType.ASSET, 1000000.0)
                : accountService.getOrCreateAccount("1003", "Cash", AccountType.ASSET, 100000.0);

        Journal journal = new Journal(
                "JRNL-EXP-" + System.currentTimeMillis(),
                JournalType.EXPENSE,
                LocalDateTime.now(),
                description != null ? description : "Expense: " + expenseAccountName,
                "EXP-" + System.currentTimeMillis()
        );

        JournalEntry debitEntry = new JournalEntry(journal, expenseAcc, amount, 0.0, "Debit: " + expenseAccountName);
        JournalEntry creditEntry = new JournalEntry(journal, payAcc, 0.0, amount, "Credit: " + payAcc.getAccountName());

        journal.addEntry(debitEntry);
        journal.addEntry(creditEntry);

        accountService.updateAccountBalance(expenseAcc, amount, true);
        accountService.updateAccountBalance(payAcc, amount, false);

        return journalRepository.save(journal);
    }
}
