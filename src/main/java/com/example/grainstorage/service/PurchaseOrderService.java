package com.example.grainstorage.service;

import com.example.grainstorage.dto.request.PurchaseOrderItemRequest;
import com.example.grainstorage.dto.request.PurchaseOrderRequest;
import com.example.grainstorage.entity.Contact;
import com.example.grainstorage.entity.Product;
import com.example.grainstorage.entity.PurchaseOrder;
import com.example.grainstorage.entity.PurchaseOrderItem;
import com.example.grainstorage.entity.Warehouse;
import com.example.grainstorage.entity.enums.PurchaseOrderStatus;
import com.example.grainstorage.exception.ResourceNotFoundException;
import com.example.grainstorage.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ContactService contactService;
    private final WarehouseService warehouseService;
    private final ProductService productService;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository,
                                ContactService contactService,
                                WarehouseService warehouseService,
                                ProductService productService) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.contactService = contactService;
        this.warehouseService = warehouseService;
        this.productService = productService;
    }

    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    public PurchaseOrder getPurchaseOrderById(Long id) {
        return purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase Order not found with id: " + id));
    }

    @Transactional
    public PurchaseOrder createPurchaseOrder(PurchaseOrderRequest request) {
        Contact vendor = contactService.getContactById(request.getVendorId());
        Warehouse warehouse = warehouseService.getWarehouseById(request.getWarehouseId());

        String poNumber = request.getPoNumber();
        if (poNumber == null || poNumber.isBlank()) {
            poNumber = "PO-" + System.currentTimeMillis();
        }

        PurchaseOrder po = new PurchaseOrder(poNumber, vendor, warehouse, request.getOrderDate());

        for (PurchaseOrderItemRequest itemReq : request.getItems()) {
            Product product = productService.getProductById(itemReq.getProductId());
            PurchaseOrderItem item = new PurchaseOrderItem(po, product, itemReq.getQuantity(), itemReq.getUnitPrice());
            po.addItem(item);
        }

        return purchaseOrderRepository.save(po);
    }

    @Transactional
    public PurchaseOrder updateStatus(Long id, PurchaseOrderStatus status) {
        PurchaseOrder po = getPurchaseOrderById(id);
        po.setStatus(status);
        return purchaseOrderRepository.save(po);
    }
}
