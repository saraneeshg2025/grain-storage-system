package com.example.grainstorage.repository;

import com.example.grainstorage.entity.VendorBill;
import com.example.grainstorage.entity.enums.BillPaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendorBillRepository extends JpaRepository<VendorBill, Long> {
    Optional<VendorBill> findByBillNumber(String billNumber);
    List<VendorBill> findByVendorId(Long vendorId);
    Optional<VendorBill> findByPurchaseOrderId(Long purchaseOrderId);
    List<VendorBill> findByPaymentStatus(BillPaymentStatus paymentStatus);
}
