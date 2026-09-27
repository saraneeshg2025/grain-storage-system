package com.example.grainstorage.repository;

import com.example.grainstorage.entity.Account;
import com.example.grainstorage.entity.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountCode(String accountCode);
    Optional<Account> findByAccountName(String accountName);
    List<Account> findByAccountType(AccountType accountType);
}
