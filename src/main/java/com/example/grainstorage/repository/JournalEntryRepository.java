package com.example.grainstorage.repository;

import com.example.grainstorage.entity.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {
    List<JournalEntry> findByJournalId(Long journalId);
    List<JournalEntry> findByAccountId(Long accountId);

    @Query("SELECT COALESCE(SUM(e.debit), 0.0) FROM JournalEntry e WHERE e.account.id = :accountId")
    Double sumDebitsByAccountId(@Param("accountId") Long accountId);

    @Query("SELECT COALESCE(SUM(e.credit), 0.0) FROM JournalEntry e WHERE e.account.id = :accountId")
    Double sumCreditsByAccountId(@Param("accountId") Long accountId);
}
