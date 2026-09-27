package com.example.grainstorage.controller;

import com.example.grainstorage.dto.request.JournalRequest;
import com.example.grainstorage.dto.response.ApiResponse;
import com.example.grainstorage.entity.Journal;
import com.example.grainstorage.entity.enums.JournalType;
import com.example.grainstorage.service.AccountingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/journals")
@CrossOrigin(origins = "*")
public class JournalController {

    private final AccountingService accountingService;

    public JournalController(AccountingService accountingService) {
        this.accountingService = accountingService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Journal>> createJournal(@Valid @RequestBody JournalRequest request) {
        Journal journal = accountingService.createJournal(request);
        return new ResponseEntity<>(ApiResponse.ok("Double-entry journal posted successfully", journal), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Journal>>> getAllJournals(@RequestParam(required = false) JournalType journalType) {
        List<Journal> list = (journalType != null)
                ? accountingService.getJournalsByType(journalType)
                : accountingService.getAllJournals();
        return ResponseEntity.ok(ApiResponse.ok(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Journal>> getJournalById(@PathVariable Long id) {
        Journal journal = accountingService.getJournalById(id);
        return ResponseEntity.ok(ApiResponse.ok(journal));
    }
}
