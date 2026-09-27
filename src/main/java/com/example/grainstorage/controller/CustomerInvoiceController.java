package com.example.grainstorage.controller;

import com.example.grainstorage.dto.request.CustomerInvoiceRequest;
import com.example.grainstorage.dto.response.ApiResponse;
import com.example.grainstorage.entity.CustomerInvoice;
import com.example.grainstorage.service.CustomerInvoiceService;
import com.example.grainstorage.service.SalesOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer-invoices")
@CrossOrigin(origins = "*")
public class CustomerInvoiceController {

    private final CustomerInvoiceService customerInvoiceService;
    private final SalesOrderService salesOrderService;

    public CustomerInvoiceController(CustomerInvoiceService customerInvoiceService,
                                     SalesOrderService salesOrderService) {
        this.customerInvoiceService = customerInvoiceService;
        this.salesOrderService = salesOrderService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CustomerInvoice>> createCustomerInvoice(@Valid @RequestBody CustomerInvoiceRequest request) {
        var salesOrder = salesOrderService.getSalesOrderById(request.getSalesOrderId());
        CustomerInvoice invoice = customerInvoiceService.createCustomerInvoice(request, salesOrder);
        return new ResponseEntity<>(ApiResponse.ok("Customer Invoice created and sales journal recorded", invoice), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerInvoice>>> getAllCustomerInvoices() {
        List<CustomerInvoice> invoices = customerInvoiceService.getAllCustomerInvoices();
        return ResponseEntity.ok(ApiResponse.ok(invoices));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerInvoice>> getCustomerInvoiceById(@PathVariable Long id) {
        CustomerInvoice invoice = customerInvoiceService.getCustomerInvoiceById(id);
        return ResponseEntity.ok(ApiResponse.ok(invoice));
    }
}
