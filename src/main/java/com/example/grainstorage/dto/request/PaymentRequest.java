package com.example.grainstorage.dto.request;

import com.example.grainstorage.entity.enums.PaymentCategory;
import com.example.grainstorage.entity.enums.PaymentType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class PaymentRequest {

    private String paymentNumber;

    @NotNull(message = "Party ID (vendor or customer) is required")
    private Long partyId;

    @NotNull(message = "Payment amount is required")
    @DecimalMin(value = "0.01", message = "Payment amount must be greater than zero")
    private Double amount;

    private LocalDateTime paymentDate;

    @NotNull(message = "Payment type is required (BANK or CASH)")
    private PaymentType paymentType;

    @NotNull(message = "Payment category is required (VENDOR_PAYMENT or CUSTOMER_RECEIPT)")
    private PaymentCategory paymentCategory;

    private String referenceNumber;

    private Long vendorBillId;

    private Long customerInvoiceId;

    public PaymentRequest() {
    }

    public String getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public Long getPartyId() {
        return partyId;
    }

    public void setPartyId(Long partyId) {
        this.partyId = partyId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public PaymentCategory getPaymentCategory() {
        return paymentCategory;
    }

    public void setPaymentCategory(PaymentCategory paymentCategory) {
        this.paymentCategory = paymentCategory;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public Long getVendorBillId() {
        return vendorBillId;
    }

    public void setVendorBillId(Long vendorBillId) {
        this.vendorBillId = vendorBillId;
    }

    public Long getCustomerInvoiceId() {
        return customerInvoiceId;
    }

    public void setCustomerInvoiceId(Long customerInvoiceId) {
        this.customerInvoiceId = customerInvoiceId;
    }
}
