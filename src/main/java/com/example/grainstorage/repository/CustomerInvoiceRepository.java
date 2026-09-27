package com.example.grainstorage.repository;

import com.example.grainstorage.entity.CustomerInvoice;
import com.example.grainstorage.entity.enums.BillPaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerInvoiceRepository extends JpaRepository<CustomerInvoice, Long> {
    Optional<CustomerInvoice> findByInvoiceNumber(String invoiceNumber);
    Optional<CustomerInvoice> findBySalesOrderId(Long salesOrderId);
    List<CustomerInvoice> findByCustomerId(Long customerId);
    List<CustomerInvoice> findByPaymentStatus(BillPaymentStatus paymentStatus);
}
