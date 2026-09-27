package com.example.grainstorage.service;

import com.example.grainstorage.dto.request.CustomerInvoiceRequest;
import com.example.grainstorage.entity.Contact;
import com.example.grainstorage.entity.CustomerInvoice;
import com.example.grainstorage.entity.SalesOrder;
import com.example.grainstorage.exception.ResourceNotFoundException;
import com.example.grainstorage.repository.CustomerInvoiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerInvoiceService {

    private final CustomerInvoiceRepository customerInvoiceRepository;
    private final ContactService contactService;
    private final AccountingService accountingService;

    public CustomerInvoiceService(CustomerInvoiceRepository customerInvoiceRepository,
                                  ContactService contactService,
                                  AccountingService accountingService) {
        this.customerInvoiceRepository = customerInvoiceRepository;
        this.contactService = contactService;
        this.accountingService = accountingService;
    }

    public List<CustomerInvoice> getAllCustomerInvoices() {
        return customerInvoiceRepository.findAll();
    }

    public CustomerInvoice getCustomerInvoiceById(Long id) {
        return customerInvoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer invoice not found with id: " + id));
    }

    @Transactional
    public CustomerInvoice createCustomerInvoice(CustomerInvoiceRequest request, SalesOrder salesOrder) {
        Contact customer = contactService.getContactById(request.getCustomerId());

        String invoiceNumber = request.getInvoiceNumber();
        if (invoiceNumber == null || invoiceNumber.isBlank()) {
            invoiceNumber = "INV-" + System.currentTimeMillis();
        }

        CustomerInvoice invoice = new CustomerInvoice(
                invoiceNumber,
                salesOrder,
                customer,
                request.getAmount(),
                request.getInvoiceDate()
        );

        CustomerInvoice savedInvoice = customerInvoiceRepository.save(invoice);

        // Record double-entry journal for sales
        // Debit: Customer Receivable / Bank
        // Credit: PDS Distribution Sales
        accountingService.recordSalesJournal(
                savedInvoice.getInvoiceNumber(),
                savedInvoice.getAmount(),
                "Distribution Sales invoice: " + savedInvoice.getInvoiceNumber() + " to " + customer.getName()
        );

        return savedInvoice;
    }

    @Transactional
    public void recordPaymentOnInvoice(Long invoiceId, Double paymentAmount) {
        CustomerInvoice invoice = getCustomerInvoiceById(invoiceId);
        double newPaid = invoice.getPaidAmount() + paymentAmount;
        invoice.setPaidAmount(newPaid);
        customerInvoiceRepository.save(invoice);
    }
}
