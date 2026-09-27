package com.example.grainstorage.dto.request;

import com.example.grainstorage.entity.enums.GrainGrade;
import com.example.grainstorage.entity.enums.GrainType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductRequest {

    @NotBlank(message = "Product code is required")
    private String productCode;

    @NotBlank(message = "Product name is required")
    private String productName;

    @NotNull(message = "Grain type is required (RICE, WHEAT, MAIZE, OTHER)")
    private GrainType grainType;

    private GrainGrade defaultGrade = GrainGrade.GRADE_A;

    @NotBlank(message = "Unit is required (e.g. KG, QUINTAL, MT)")
    private String unit = "KG";

    private String description;

    public ProductRequest() {
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
