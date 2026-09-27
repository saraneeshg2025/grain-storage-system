package com.example.grainstorage.entity;

import com.example.grainstorage.entity.enums.GrainGrade;
import com.example.grainstorage.entity.enums.LotStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "grain_lots")
public class GrainLot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lot_number", nullable = false, unique = true, length = 50)
    private String lotNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Contact vendor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "moisture_percentage", nullable = false)
    private Double moisturePercentage;

    @Column(name = "impurity_percentage", nullable = false)
    private Double impurityPercentage;

    @Column(name = "quality_score")
    private Double qualityScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade", length = 30)
    private GrainGrade grade;

    @Column(name = "procurement_date", nullable = false)
    private LocalDateTime procurementDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private LotStatus status = LotStatus.RECEIVED;

    public GrainLot() {
    }

    public GrainLot(String lotNumber, Product product, Contact vendor, Warehouse warehouse,
                    Double quantity, Double unitPrice, Double moisturePercentage, Double impurityPercentage) {
        this.lotNumber = lotNumber;
        this.product = product;
        this.vendor = vendor;
        this.warehouse = warehouse;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalAmount = (quantity != null && unitPrice != null) ? quantity * unitPrice : 0.0;
        this.moisturePercentage = moisturePercentage;
        this.impurityPercentage = impurityPercentage;
        this.procurementDate = LocalDateTime.now();
        this.status = LotStatus.RECEIVED;
    }

    @PrePersist
    protected void onCreate() {
        if (this.procurementDate == null) {
            this.procurementDate = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = LotStatus.RECEIVED;
        }
        if (this.quantity != null && this.unitPrice != null) {
            this.totalAmount = this.quantity * this.unitPrice;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        if (this.quantity != null && this.unitPrice != null) {
            this.totalAmount = this.quantity * this.unitPrice;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Contact getVendor() {
        return vendor;
    }

    public void setVendor(Contact vendor) {
        this.vendor = vendor;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
        if (this.unitPrice != null) {
            this.totalAmount = quantity * this.unitPrice;
        }
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
        if (this.quantity != null) {
            this.totalAmount = this.quantity * unitPrice;
        }
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getMoisturePercentage() {
        return moisturePercentage;
    }

    public void setMoisturePercentage(Double moisturePercentage) {
        this.moisturePercentage = moisturePercentage;
    }

    public Double getImpurityPercentage() {
        return impurityPercentage;
    }

    public void setImpurityPercentage(Double impurityPercentage) {
        this.impurityPercentage = impurityPercentage;
    }

    public Double getQualityScore() {
        return qualityScore;
    }

    public void setQualityScore(Double qualityScore) {
        this.qualityScore = qualityScore;
    }

    public GrainGrade getGrade() {
        return grade;
    }

    public void setGrade(GrainGrade grade) {
        this.grade = grade;
    }

    public LocalDateTime getProcurementDate() {
        return procurementDate;
    }

    public void setProcurementDate(LocalDateTime procurementDate) {
        this.procurementDate = procurementDate;
    }

    public LotStatus getStatus() {
        return status;
    }

    public void setStatus(LotStatus status) {
        this.status = status;
    }
}
