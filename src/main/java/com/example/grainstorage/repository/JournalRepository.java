package com.example.grainstorage.repository;

import com.example.grainstorage.entity.Journal;
import com.example.grainstorage.entity.enums.JournalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Long> {
    Optional<Journal> findByJournalNumber(String journalNumber);
    List<Journal> findByJournalType(JournalType journalType);
}
