package com.example.grainstorage.service;

import com.example.grainstorage.dto.request.GrainLotProcureRequest;
import com.example.grainstorage.dto.request.QualityGradeRequest;
import com.example.grainstorage.dto.response.QualityGradeResponse;
import com.example.grainstorage.entity.Contact;
import com.example.grainstorage.entity.GrainLot;
import com.example.grainstorage.entity.Product;
import com.example.grainstorage.entity.Warehouse;
import com.example.grainstorage.entity.enums.GrainGrade;
import com.example.grainstorage.entity.enums.LotStatus;
import com.example.grainstorage.exception.InsufficientCapacityException;
import com.example.grainstorage.exception.InvalidTransactionException;
import com.example.grainstorage.exception.ResourceNotFoundException;
import com.example.grainstorage.repository.GrainLotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GrainLotService {

    private final GrainLotRepository grainLotRepository;
    private final WarehouseService warehouseService;
    private final ProductService productService;
    private final ContactService contactService;
    private final QualityGradingService qualityGradingService;
    private final InventoryService inventoryService;

    public GrainLotService(GrainLotRepository grainLotRepository,
                           WarehouseService warehouseService,
                           ProductService productService,
                           ContactService contactService,
                           QualityGradingService qualityGradingService,
                           InventoryService inventoryService) {
        this.grainLotRepository = grainLotRepository;
        this.warehouseService = warehouseService;
        this.productService = productService;
        this.contactService = contactService;
        this.qualityGradingService = qualityGradingService;
        this.inventoryService = inventoryService;
    }

    public List<GrainLot> getAllGrainLots() {
        return grainLotRepository.findAll();
    }

    public GrainLot getGrainLotById(Long id) {
        return grainLotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grain lot not found with id: " + id));
    }

    public List<GrainLot> getGrainLotsByWarehouse(Long warehouseId) {
        return grainLotRepository.findByWarehouseId(warehouseId);
    }

    @Transactional
    public GrainLot procureGrainLot(GrainLotProcureRequest request) {
        Product product = productService.getProductById(request.getProductId());
        Contact vendor = contactService.getContactById(request.getVendorId());
        Warehouse warehouse = warehouseService.getWarehouseById(request.getWarehouseId());

        String lotNumber = request.getLotNumber();
        if (lotNumber == null || lotNumber.isBlank()) {
            lotNumber = "LOT-" + System.currentTimeMillis();
        }

        // Automatic Quality Grading
        QualityGradeResponse gradeResult = qualityGradingService.evaluateQuality(
                request.getMoisturePercentage(),
                request.getImpurityPercentage()
        );

        GrainLot lot = new GrainLot(
                lotNumber,
                product,
                vendor,
                warehouse,
                request.getQuantity(),
                request.getUnitPrice(),
                request.getMoisturePercentage(),
                request.getImpurityPercentage()
        );

        lot.setQualityScore(gradeResult.getQualityScore());
        lot.setGrade(gradeResult.getGrade());
        lot.setStatus(gradeResult.isAccepted() ? LotStatus.GRADED : LotStatus.REJECTED);

        GrainLot savedLot = grainLotRepository.save(lot);

        // Auto-store if requested and grade is accepted
        if (Boolean.TRUE.equals(request.getAutoStore()) && gradeResult.isAccepted()) {
            storeGrainLot(savedLot.getId());
        }

        return savedLot;
    }

    @Transactional
    public GrainLot gradeGrainLot(Long id, QualityGradeRequest request) {
        GrainLot lot = getGrainLotById(id);

        if (lot.getStatus() == LotStatus.STORED || lot.getStatus() == LotStatus.DISTRIBUTED) {
            throw new InvalidTransactionException("Cannot re-grade grain lot with status: " + lot.getStatus());
        }

        QualityGradeResponse gradeResult = qualityGradingService.evaluateQuality(
                request.getMoisturePercentage(),
                request.getImpurityPercentage()
        );

        lot.setMoisturePercentage(request.getMoisturePercentage());
        lot.setImpurityPercentage(request.getImpurityPercentage());
        lot.setQualityScore(gradeResult.getQualityScore());
        lot.setGrade(gradeResult.getGrade());
        lot.setStatus(gradeResult.isAccepted() ? LotStatus.GRADED : LotStatus.REJECTED);

        return grainLotRepository.save(lot);
    }

    @Transactional
    public GrainLot storeGrainLot(Long id) {
        GrainLot lot = getGrainLotById(id);

        if (lot.getStatus() == LotStatus.REJECTED) {
            throw new InvalidTransactionException("Cannot store rejected grain lot! Quality score: " + lot.getQualityScore());
        }
        if (lot.getStatus() == LotStatus.STORED) {
            throw new InvalidTransactionException("Grain lot is already stored in warehouse");
        }

        Warehouse warehouse = lot.getWarehouse();

        // Capacity validation
        warehouseService.validateCapacity(warehouse.getId(), lot.getQuantity());

        // Update warehouse capacity
        warehouseService.increaseUsedCapacity(warehouse.getId(), lot.getQuantity());

        // Add to inventory
        inventoryService.addInventory(warehouse, lot.getProduct(), lot, lot.getQuantity(), lot.getGrade());

        lot.setStatus(LotStatus.STORED);
        return grainLotRepository.save(lot);
    }
}
