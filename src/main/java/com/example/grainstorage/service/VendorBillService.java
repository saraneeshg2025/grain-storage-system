package com.example.grainstorage.service;

import com.example.grainstorage.dto.request.VendorBillRequest;
import com.example.grainstorage.entity.Contact;
import com.example.grainstorage.entity.PurchaseOrder;
import com.example.grainstorage.entity.VendorBill;
import com.example.grainstorage.entity.enums.BillPaymentStatus;
import com.example.grainstorage.exception.ResourceNotFoundException;
import com.example.grainstorage.repository.VendorBillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VendorBillService {

    private final VendorBillRepository vendorBillRepository;
    private final ContactService contactService;
    private final PurchaseOrderService purchaseOrderService;
    private final AccountingService accountingService;

    public VendorBillService(VendorBillRepository vendorBillRepository,
                             ContactService contactService,
                             PurchaseOrderService purchaseOrderService,
                             AccountingService accountingService) {
        this.vendorBillRepository = vendorBillRepository;
        this.contactService = contactService;
        this.purchaseOrderService = purchaseOrderService;
        this.accountingService = accountingService;
    }

    public List<VendorBill> getAllVendorBills() {
        return vendorBillRepository.findAll();
    }

    public VendorBill getVendorBillById(Long id) {
        return vendorBillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor bill not found with id: " + id));
    }

    @Transactional
    public VendorBill createVendorBill(VendorBillRequest request) {
        Contact vendor = contactService.getContactById(request.getVendorId());
        PurchaseOrder po = null;
        if (request.getPurchaseOrderId() != null) {
            po = purchaseOrderService.getPurchaseOrderById(request.getPurchaseOrderId());
        }

        String billNumber = request.getBillNumber();
        if (billNumber == null || billNumber.isBlank()) {
            billNumber = "BILL-" + System.currentTimeMillis();
        }

        VendorBill bill = new VendorBill(billNumber, po, vendor, request.getAmount(), request.getBillDate());
        VendorBill savedBill = vendorBillRepository.save(bill);

        // Record double-entry journal for purchase
        // Debit: Grain Stock Inventory (Asset)
        // Credit: Farmer Creditors (Liability)
        String ref = po != null ? po.getPoNumber() : savedBill.getBillNumber();
        accountingService.recordPurchaseJournal(ref, savedBill.getAmount(), "Vendor Bill: " + savedBill.getBillNumber() + " for " + vendor.getName());

        return savedBill;
    }

    @Transactional
    public void recordPaymentOnBill(Long billId, Double paymentAmount) {
        VendorBill bill = getVendorBillById(billId);
        double newPaid = bill.getPaidAmount() + paymentAmount;
        bill.setPaidAmount(newPaid);
        vendorBillRepository.save(bill);
    }
}
