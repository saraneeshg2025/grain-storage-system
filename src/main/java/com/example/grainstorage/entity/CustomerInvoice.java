package com.example.grainstorage.entity;

import com.example.grainstorage.entity.enums.BillPaymentStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_invoices")
public class CustomerInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_number", nullable = false, unique = true, length = 50)
    private String invoiceNumber;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sales_order_id", unique = true)
    private SalesOrder salesOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false)
    private Contact customer;

    @Column(name = "invoice_date", nullable = false)
    private LocalDateTime invoiceDate;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "paid_amount", nullable = false)
    private Double paidAmount = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 30)
    private BillPaymentStatus paymentStatus = BillPaymentStatus.PENDING;

    public CustomerInvoice() {
    }

    public CustomerInvoice(String invoiceNumber, SalesOrder salesOrder, Contact customer, Double amount, LocalDateTime invoiceDate) {
        this.invoiceNumber = invoiceNumber;
        this.salesOrder = salesOrder;
        this.customer = customer;
        this.amount = amount;
        this.paidAmount = 0.0;
        this.invoiceDate = invoiceDate != null ? invoiceDate : LocalDateTime.now();
        this.paymentStatus = BillPaymentStatus.PENDING;
    }

    @PrePersist
    protected void onCreate() {
        if (this.invoiceDate == null) {
            this.invoiceDate = LocalDateTime.now();
        }
        if (this.paidAmount == null) {
            this.paidAmount = 0.0;
        }
        if (this.paymentStatus == null) {
            this.paymentStatus = BillPaymentStatus.PENDING;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public SalesOrder getSalesOrder() {
        return salesOrder;
    }

    public void setSalesOrder(SalesOrder salesOrder) {
        this.salesOrder = salesOrder;
    }

    public Contact getCustomer() {
        return customer;
    }

    public void setCustomer(Contact customer) {
        this.customer = customer;
    }

    public LocalDateTime getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDateTime invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
        if (this.amount != null) {
            if (this.paidAmount >= this.amount) {
                this.paymentStatus = BillPaymentStatus.PAID;
            } else if (this.paidAmount > 0) {
                this.paymentStatus = BillPaymentStatus.PARTIALLY_PAID;
            } else {
                this.paymentStatus = BillPaymentStatus.PENDING;
            }
        }
    }

    public BillPaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(BillPaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
