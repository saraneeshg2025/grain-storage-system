package com.example.grainstorage.repository;

import com.example.grainstorage.entity.Product;
import com.example.grainstorage.entity.enums.GrainType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductCode(String productCode);
    List<Product> findByGrainType(GrainType grainType);
}
