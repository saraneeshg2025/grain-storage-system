package com.example.grainstorage.controller;

import com.example.grainstorage.dto.request.AccountRequest;
import com.example.grainstorage.dto.response.ApiResponse;
import com.example.grainstorage.entity.Account;
import com.example.grainstorage.entity.enums.AccountType;
import com.example.grainstorage.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Account>> createAccount(@Valid @RequestBody AccountRequest request) {
        Account account = accountService.createAccount(request);
        return new ResponseEntity<>(ApiResponse.ok("Chart of Account created successfully", account), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Account>>> getAllAccounts(@RequestParam(required = false) AccountType accountType) {
        List<Account> accounts = accountService.getAllAccounts(accountType);
        return ResponseEntity.ok(ApiResponse.ok(accounts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Account>> getAccountById(@PathVariable Long id) {
        Account account = accountService.getAccountById(id);
        return ResponseEntity.ok(ApiResponse.ok(account));
    }
}
