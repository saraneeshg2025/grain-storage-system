package com.example.grainstorage.entity;

import com.example.grainstorage.entity.enums.BillPaymentStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vendor_bills")
public class VendorBill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bill_number", nullable = false, unique = true, length = 50)
    private String billNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Contact vendor;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "paid_amount", nullable = false)
    private Double paidAmount = 0.0;

    @Column(name = "bill_date", nullable = false)
    private LocalDateTime billDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 30)
    private BillPaymentStatus paymentStatus = BillPaymentStatus.PENDING;

    public VendorBill() {
    }

    public VendorBill(String billNumber, PurchaseOrder purchaseOrder, Contact vendor, Double amount, LocalDateTime billDate) {
        this.billNumber = billNumber;
        this.purchaseOrder = purchaseOrder;
        this.vendor = vendor;
        this.amount = amount;
        this.paidAmount = 0.0;
        this.billDate = billDate != null ? billDate : LocalDateTime.now();
        this.paymentStatus = BillPaymentStatus.PENDING;
    }

    @PrePersist
    protected void onCreate() {
        if (this.billDate == null) {
            this.billDate = LocalDateTime.now();
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

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public Contact getVendor() {
        return vendor;
    }

    public void setVendor(Contact vendor) {
        this.vendor = vendor;
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

    public LocalDateTime getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDateTime billDate) {
        this.billDate = billDate;
    }

    public BillPaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(BillPaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
