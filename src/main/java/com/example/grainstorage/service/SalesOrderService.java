package com.example.grainstorage.service;

import com.example.grainstorage.dto.request.CustomerInvoiceRequest;
import com.example.grainstorage.dto.request.SalesOrderItemRequest;
import com.example.grainstorage.dto.request.SalesOrderRequest;
import com.example.grainstorage.entity.Contact;
import com.example.grainstorage.entity.CustomerInvoice;
import com.example.grainstorage.entity.Product;
import com.example.grainstorage.entity.SalesOrder;
import com.example.grainstorage.entity.SalesOrderItem;
import com.example.grainstorage.entity.Warehouse;
import com.example.grainstorage.entity.enums.SalesOrderStatus;
import com.example.grainstorage.exception.InsufficientInventoryException;
import com.example.grainstorage.exception.InvalidTransactionException;
import com.example.grainstorage.exception.ResourceNotFoundException;
import com.example.grainstorage.repository.SalesOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;
    private final ContactService contactService;
    private final WarehouseService warehouseService;
    private final ProductService productService;
    private final InventoryService inventoryService;
    private final CustomerInvoiceService customerInvoiceService;

    public SalesOrderService(SalesOrderRepository salesOrderRepository,
                             ContactService contactService,
                             WarehouseService warehouseService,
                             ProductService productService,
                             InventoryService inventoryService,
                             CustomerInvoiceService customerInvoiceService) {
        this.salesOrderRepository = salesOrderRepository;
        this.contactService = contactService;
        this.warehouseService = warehouseService;
        this.productService = productService;
        this.inventoryService = inventoryService;
        this.customerInvoiceService = customerInvoiceService;
    }

    public List<SalesOrder> getAllSalesOrders() {
        return salesOrderRepository.findAll();
    }

    public SalesOrder getSalesOrderById(Long id) {
        return salesOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sales order not found with id: " + id));
    }

    @Transactional
    public SalesOrder createSalesOrder(SalesOrderRequest request) {
        Contact customer = contactService.getContactById(request.getCustomerId());
        Warehouse warehouse = warehouseService.getWarehouseById(request.getWarehouseId());

        String soNumber = request.getSalesOrderNumber();
        if (soNumber == null || soNumber.isBlank()) {
            soNumber = "SO-" + System.currentTimeMillis();
        }

        SalesOrder order = new SalesOrder(soNumber, customer, warehouse, request.getOrderDate());

        for (SalesOrderItemRequest itemReq : request.getItems()) {
            Product product = productService.getProductById(itemReq.getProductId());
            SalesOrderItem item = new SalesOrderItem(order, product, itemReq.getQuantity(), itemReq.getUnitPrice());
            order.addItem(item);
        }

        return salesOrderRepository.save(order);
    }

    @Transactional
    public SalesOrder updateStatus(Long id, SalesOrderStatus status) {
        SalesOrder order = getSalesOrderById(id);
        order.setStatus(status);
        return salesOrderRepository.save(order);
    }

    @Transactional
    public CustomerInvoice dispatchOrder(Long id) {
        SalesOrder order = getSalesOrderById(id);

        if (order.getStatus() == SalesOrderStatus.DISPATCHED || order.getStatus() == SalesOrderStatus.COMPLETED) {
            throw new InvalidTransactionException("Sales order has already been dispatched/completed");
        }
        if (order.getStatus() == SalesOrderStatus.CANCELLED) {
            throw new InvalidTransactionException("Cannot dispatch a cancelled sales order");
        }

        Long warehouseId = order.getWarehouse().getId();

        // 1. Verify available inventory for all items in order
        for (SalesOrderItem item : order.getItems()) {
            Double availableStock = inventoryService.getAvailableQuantityByWarehouseAndProduct(warehouseId, item.getProduct().getId());
            if (availableStock < item.getQuantity()) {
                throw new InsufficientInventoryException(
                        String.format("Insufficient stock for product %s in warehouse %s. Available: %.2f kg, Required: %.2f kg",
                                item.getProduct().getProductName(), order.getWarehouse().getWarehouseName(), availableStock, item.getQuantity())
                );
            }
        }

        // 2. Reduce inventory and decrease warehouse used capacity
        double totalQuantityDispatched = 0.0;
        for (SalesOrderItem item : order.getItems()) {
            inventoryService.reduceInventory(warehouseId, item.getProduct().getId(), item.getQuantity());
            totalQuantityDispatched += item.getQuantity();
        }
        warehouseService.decreaseUsedCapacity(warehouseId, totalQuantityDispatched);

        // 3. Mark sales order as DISPATCHED / COMPLETED
        order.setStatus(SalesOrderStatus.DISPATCHED);
        salesOrderRepository.save(order);

        // 4. Generate customer invoice
        CustomerInvoiceRequest invoiceRequest = new CustomerInvoiceRequest();
        invoiceRequest.setSalesOrderId(order.getId());
        invoiceRequest.setCustomerId(order.getCustomer().getId());
        invoiceRequest.setAmount(order.getTotalAmount());
        invoiceRequest.setInvoiceDate(LocalDateTime.now());

        return customerInvoiceService.createCustomerInvoice(invoiceRequest, order);
    }
}
