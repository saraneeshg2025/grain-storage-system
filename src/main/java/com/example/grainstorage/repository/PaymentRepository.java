package com.example.grainstorage.repository;

import com.example.grainstorage.entity.Payment;
import com.example.grainstorage.entity.enums.PaymentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByPaymentNumber(String paymentNumber);
    List<Payment> findByPartyId(Long partyId);
    List<Payment> findByPaymentCategory(PaymentCategory paymentCategory);
}
