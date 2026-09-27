package com.example.grainstorage.service;

import com.example.grainstorage.dto.request.PaymentRequest;
import com.example.grainstorage.entity.Contact;
import com.example.grainstorage.entity.CustomerInvoice;
import com.example.grainstorage.entity.Payment;
import com.example.grainstorage.entity.VendorBill;
import com.example.grainstorage.entity.enums.PaymentCategory;
import com.example.grainstorage.entity.enums.PaymentType;
import com.example.grainstorage.exception.ResourceNotFoundException;
import com.example.grainstorage.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ContactService contactService;
    private final VendorBillService vendorBillService;
    private final CustomerInvoiceService customerInvoiceService;
    private final AccountingService accountingService;

    public PaymentService(PaymentRepository paymentRepository,
                          ContactService contactService,
                          VendorBillService vendorBillService,
                          CustomerInvoiceService customerInvoiceService,
                          AccountingService accountingService) {
        this.paymentRepository = paymentRepository;
        this.contactService = contactService;
        this.vendorBillService = vendorBillService;
        this.customerInvoiceService = customerInvoiceService;
        this.accountingService = accountingService;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
    }

    @Transactional
    public Payment recordPayment(PaymentRequest request) {
        Contact party = contactService.getContactById(request.getPartyId());

        String paymentNumber = request.getPaymentNumber();
        if (paymentNumber == null || paymentNumber.isBlank()) {
            paymentNumber = "PAY-" + System.currentTimeMillis();
        }

        Payment payment = new Payment(
                paymentNumber,
                party,
                request.getAmount(),
                request.getPaymentDate(),
                request.getPaymentType(),
                request.getPaymentCategory(),
                request.getReferenceNumber()
        );

        boolean isBank = request.getPaymentType() == PaymentType.BANK;

        if (request.getPaymentCategory() == PaymentCategory.VENDOR_PAYMENT) {
            // Case 1: Payment to farmer/vendor
            if (request.getVendorBillId() != null) {
                VendorBill bill = vendorBillService.getVendorBillById(request.getVendorBillId());
                payment.setVendorBill(bill);
                vendorBillService.recordPaymentOnBill(bill.getId(), request.getAmount());
            }

            // Journal: Debit Farmer Creditors, Credit Bank/Cash
            accountingService.recordFarmerPaymentJournal(
                    paymentNumber,
                    request.getAmount(),
                    request.getReferenceNumber() != null ? request.getReferenceNumber() : paymentNumber,
                    isBank
            );

        } else if (request.getPaymentCategory() == PaymentCategory.CUSTOMER_RECEIPT) {
            // Case 2: Receipt from PDS distribution customer
            if (request.getCustomerInvoiceId() != null) {
                CustomerInvoice invoice = customerInvoiceService.getCustomerInvoiceById(request.getCustomerInvoiceId());
                payment.setCustomerInvoice(invoice);
                customerInvoiceService.recordPaymentOnInvoice(invoice.getId(), request.getAmount());
            }

            // Journal: Debit Bank/Cash, Credit Customer Receivable
            accountingService.recordCustomerReceiptJournal(
                    paymentNumber,
                    request.getAmount(),
                    request.getReferenceNumber() != null ? request.getReferenceNumber() : paymentNumber,
                    isBank
            );
        }

        return paymentRepository.save(payment);
    }
}
