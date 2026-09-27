package com.example.grainstorage.entity;

import com.example.grainstorage.entity.enums.GrainGrade;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventories")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grain_lot_id", unique = true)
    private GrainLot grainLot;

    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade", nullable = false, length = 30)
    private GrainGrade grade;

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    public Inventory() {
    }

    public Inventory(Warehouse warehouse, Product product, GrainLot grainLot, Double quantity, GrainGrade grade) {
        this.warehouse = warehouse;
        this.product = product;
        this.grainLot = grainLot;
        this.quantity = quantity;
        this.grade = grade;
        this.lastUpdated = LocalDateTime.now();
    }

    @PrePersist
    @PreUpdate
    protected void onPersistOrUpdate() {
        this.lastUpdated = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public GrainLot getGrainLot() {
        return grainLot;
    }

    public void setGrainLot(GrainLot grainLot) {
        this.grainLot = grainLot;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public GrainGrade getGrade() {
        return grade;
    }

    public void setGrade(GrainGrade grade) {
        this.grade = grade;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
