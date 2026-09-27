package com.example.grainstorage.service;

import com.example.grainstorage.dto.request.AccountRequest;
import com.example.grainstorage.entity.Account;
import com.example.grainstorage.entity.enums.AccountType;
import com.example.grainstorage.exception.ResourceNotFoundException;
import com.example.grainstorage.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Account createAccount(AccountRequest request) {
        if (accountRepository.findByAccountCode(request.getAccountCode()).isPresent()) {
            throw new IllegalArgumentException("Account code already exists: " + request.getAccountCode());
        }
        Account account = new Account(
                request.getAccountCode(),
                request.getAccountName(),
                request.getAccountType(),
                request.getOpeningBalance()
        );
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts(AccountType accountType) {
        if (accountType != null) {
            return accountRepository.findByAccountType(accountType);
        }
        return accountRepository.findAll();
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
    }

    public Account getAccountByName(String name) {
        return accountRepository.findByAccountName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with name: " + name));
    }

    public Account getAccountByCode(String code) {
        return accountRepository.findByAccountCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with code: " + code));
    }

    @Transactional
    public Account getOrCreateAccount(String code, String name, AccountType type, Double openingBalance) {
        return accountRepository.findByAccountName(name)
                .orElseGet(() -> {
                    Account newAcc = new Account(code, name, type, openingBalance != null ? openingBalance : 0.0);
                    return accountRepository.save(newAcc);
                });
    }

    @Transactional
    public void updateAccountBalance(Account account, Double amount, boolean isDebit) {
        // Double entry accounting principles:
        // Asset & Expense: Debit increases, Credit decreases
        // Liability & Income: Credit increases, Debit decreases
        AccountType type = account.getAccountType();
        double current = account.getCurrentBalance() != null ? account.getCurrentBalance() : 0.0;

        if (type == AccountType.ASSET || type == AccountType.EXPENSE) {
            if (isDebit) {
                account.setCurrentBalance(current + amount);
            } else {
                account.setCurrentBalance(current - amount);
            }
        } else { // LIABILITY or INCOME
            if (isDebit) {
                account.setCurrentBalance(current - amount);
            } else {
                account.setCurrentBalance(current + amount);
            }
        }
        accountRepository.save(account);
    }
}
