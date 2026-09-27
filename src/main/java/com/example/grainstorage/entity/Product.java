package com.example.grainstorage.entity;

import com.example.grainstorage.entity.enums.GrainGrade;
import com.example.grainstorage.entity.enums.GrainType;
import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_code", nullable = false, unique = true, length = 50)
    private String productCode;

    @Column(name = "product_name", nullable = false, length = 150)
    private String productName;

    @Enumerated(EnumType.STRING)
    @Column(name = "grain_type", nullable = false, length = 50)
    private GrainType grainType;

    @Enumerated(EnumType.STRING)
    @Column(name = "default_grade", length = 50)
    private GrainGrade defaultGrade = GrainGrade.GRADE_A;

    @Column(name = "unit", nullable = false, length = 20)
    private String unit = "KG";

    @Column(name = "description", length = 255)
    private String description;

    public Product() {
    }

    public Product(String productCode, String productName, GrainType grainType, GrainGrade defaultGrade, String unit, String description) {
        this.productCode = productCode;
        this.productName = productName;
        this.grainType = grainType;
        this.defaultGrade = defaultGrade;
        this.unit = unit;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public GrainType getGrainType() {
        return grainType;
    }

    public void setGrainType(GrainType grainType) {
        this.grainType = grainType;
    }

    public GrainGrade getDefaultGrade() {
        return defaultGrade;
    }

    public void setDefaultGrade(GrainGrade defaultGrade) {
        this.defaultGrade = defaultGrade;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
