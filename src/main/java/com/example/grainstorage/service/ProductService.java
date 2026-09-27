package com.example.grainstorage.service;

import com.example.grainstorage.dto.request.ProductRequest;
import com.example.grainstorage.entity.Product;
import com.example.grainstorage.entity.enums.GrainGrade;
import com.example.grainstorage.entity.enums.GrainType;
import com.example.grainstorage.exception.ResourceNotFoundException;
import com.example.grainstorage.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product createProduct(ProductRequest request) {
        if (productRepository.findByProductCode(request.getProductCode()).isPresent()) {
            throw new IllegalArgumentException("Product code already exists: " + request.getProductCode());
        }
        Product product = new Product(
                request.getProductCode(),
                request.getProductName(),
                request.getGrainType(),
                request.getDefaultGrade() != null ? request.getDefaultGrade() : GrainGrade.GRADE_A,
                request.getUnit(),
                request.getDescription()
        );
        return productRepository.save(product);
    }

    public List<Product> getAllProducts(GrainType grainType) {
        if (grainType != null) {
            return productRepository.findByGrainType(grainType);
        }
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    @Transactional
    public Product updateProduct(Long id, ProductRequest request) {
        Product product = getProductById(id);
        product.setProductName(request.getProductName());
        product.setGrainType(request.getGrainType());
        if (request.getDefaultGrade() != null) {
            product.setDefaultGrade(request.getDefaultGrade());
        }
        product.setUnit(request.getUnit());
        product.setDescription(request.getDescription());
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}
