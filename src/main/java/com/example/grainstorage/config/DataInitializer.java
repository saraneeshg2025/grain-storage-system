package com.example.grainstorage.config;

import com.example.grainstorage.entity.*;
import com.example.grainstorage.entity.enums.*;
import com.example.grainstorage.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final WarehouseRepository warehouseRepository;
    private final ContactRepository contactRepository;
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;
    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;
    private final SiloTelemetryRepository telemetryRepository;
    private final GrainLotRepository grainLotRepository;
    private final InventoryRepository inventoryRepository;

    public DataInitializer(WarehouseRepository warehouseRepository,
                           ContactRepository contactRepository,
                           ProductRepository productRepository,
                           AccountRepository accountRepository,
                           BudgetRepository budgetRepository,
                           UserRepository userRepository,
                           SiloTelemetryRepository telemetryRepository,
                           GrainLotRepository grainLotRepository,
                           InventoryRepository inventoryRepository) {
        this.warehouseRepository = warehouseRepository;
        this.contactRepository = contactRepository;
        this.productRepository = productRepository;
        this.accountRepository = accountRepository;
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
        this.telemetryRepository = telemetryRepository;
        this.grainLotRepository = grainLotRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public void run(String... args) {
        log.info("Checking and initializing system sample master data...");

        // 1. Initial Chart of Accounts
        createAccountIfAbsent("1001", "Grain Stock Inventory", AccountType.ASSET, 0.0);
        createAccountIfAbsent("1002", "Bank", AccountType.ASSET, 10000000.0);
        createAccountIfAbsent("1003", "Cash", AccountType.ASSET, 500000.0);
        createAccountIfAbsent("1004", "Customer Receivable", AccountType.ASSET, 0.0);
        createAccountIfAbsent("2001", "Farmer Creditors", AccountType.LIABILITY, 0.0);
        createAccountIfAbsent("3001", "PDS Distribution Sales", AccountType.INCOME, 0.0);
        createAccountIfAbsent("4001", "Grain Procurement Expenses", AccountType.EXPENSE, 0.0);
        createAccountIfAbsent("4002", "Storage Handling Expenses", AccountType.EXPENSE, 0.0);

        // 2. Initial Warehouse
        Warehouse warehouse;
        Optional<Warehouse> whOpt = warehouseRepository.findByWarehouseCode("WH-CENTRAL-03");
        if (whOpt.isEmpty()) {
            Warehouse newWh = new Warehouse(
                    "WH-CENTRAL-03",
                    "Central Grain Silo Zone 3",
                    "Sector 12, Agro Logistics Park, New Delhi",
                    100000.0
            );
            warehouse = warehouseRepository.save(newWh);
            log.info("Initialized sample Warehouse: {}", warehouse.getWarehouseName());
        } else {
            warehouse = whOpt.get();
        }

        // 3. Initial Contacts (Farmer Vendor & PDS Agency Customer)
        Contact vendor;
        if (contactRepository.findByContactType(ContactType.FARMER_COOPERATIVE).isEmpty()) {
            Contact newVendor = new Contact(
                    "Farmer Cooperative Society Punjab",
                    ContactType.FARMER_COOPERATIVE,
                    "+91-9876543210",
                    "punjab.farmers@coop.org",
                    "Grain Mandi Road, Ludhiana, Punjab"
            );
            vendor = contactRepository.save(newVendor);
            log.info("Initialized sample Vendor: {}", vendor.getName());
        } else {
            vendor = contactRepository.findByContactType(ContactType.FARMER_COOPERATIVE).get(0);
        }

        Contact customer;
        if (contactRepository.findByContactType(ContactType.PDS_DISTRIBUTION_AGENCY).isEmpty()) {
            Contact newCust = new Contact(
                    "District PDS Distribution Agency",
                    ContactType.PDS_DISTRIBUTION_AGENCY,
                    "+91-9123456780",
                    "dist.pds@gov.in",
                    "Civil Supplies Bhavan, North District"
            );
            customer = contactRepository.save(newCust);
            log.info("Initialized sample Customer: {}", customer.getName());
        } else {
            customer = contactRepository.findByContactType(ContactType.PDS_DISTRIBUTION_AGENCY).get(0);
        }

        // 4. Initial Products
        createProductIfAbsent("WHEAT-A", "Wheat Lot Grade A", GrainType.WHEAT, GrainGrade.GRADE_A, "KG", "High quality Sharbati wheat conforming to PDS specifications");
        createProductIfAbsent("RICE-A", "Rice Lot Grade A", GrainType.RICE, GrainGrade.GRADE_A, "KG", "Premium graded non-basmati parboiled rice");
        createProductIfAbsent("MAIZE-A", "Yellow Maize Lot", GrainType.MAIZE, GrainGrade.GRADE_A, "KG", "Standard feed & food grade yellow maize");

        // 5. Initial Budget
        if (budgetRepository.findAll().isEmpty()) {
            Account expenseAcc = accountRepository.findByAccountCode("4001").orElse(null);
            if (expenseAcc != null && warehouse != null) {
                Budget budget = new Budget(
                        "Annual Grain Procurement Budget",
                        "2026-27",
                        warehouse,
                        expenseAcc,
                        1000000.0
                );
                budgetRepository.save(budget);
                log.info("Initialized sample Budget: ₹10,00,000 for {}", warehouse.getWarehouseName());
            }
        }

        // 6. Initial Users
        createUserIfAbsent("admin", "admin123", "System Administrator", "admin@grainstorage.gov.in", Role.ADMIN);
        createUserIfAbsent("accountant", "account123", "Senior Accountant", "accountant@grainstorage.gov.in", Role.WAREHOUSE_ACCOUNTANT);
        createUserIfAbsent("director", "director123", "Warehouse Director", "director@grainstorage.gov.in", Role.WAREHOUSE_DIRECTOR);

        // 7. Initial Seed Grain Lot & Live Inventory (Pre-stored for instant demo)
        Product wheatProd = productRepository.findByProductCode("WHEAT-A").orElse(null);
        if (grainLotRepository.findAll().isEmpty() && wheatProd != null && vendor != null && warehouse != null) {
            GrainLot lot = new GrainLot(
                    "LOT-PDS-WHEAT-01",
                    wheatProd,
                    vendor,
                    warehouse,
                    20000.0,
                    24.50,
                    11.2,
                    1.4
            );
            lot.setQualityScore(89.5);
            lot.setGrade(GrainGrade.GRADE_A);
            lot.setStatus(LotStatus.STORED);
            GrainLot savedLot = grainLotRepository.save(lot);

            // Update warehouse capacity
            warehouse.setUsedCapacity(20000.0);
            warehouse.setAvailableCapacity(80000.0);
            warehouseRepository.save(warehouse);

            // Seed live inventory
            Inventory inv = new Inventory(warehouse, wheatProd, savedLot, 20000.0, GrainGrade.GRADE_A);
            inventoryRepository.save(inv);
            log.info("Initialized sample Grain Lot and Inventory: 20,000 kg Grade A Wheat");
        }

        // 8. Initial Silo IoT Sensor Telemetry
        if (telemetryRepository.findAll().isEmpty() && warehouse != null) {
            SiloTelemetry t1 = new SiloTelemetry(warehouse, "Chamber A1 - Silo Core", 24.5, 54.0, 480.0);
            t1.setRiskLevel(SpoilageRiskLevel.OPTIMAL);
            t1.setAerationFanActive(false);
            t1.setRecommendation("OPTIMAL: Silo micro-climate is stable. Grain quality index is 98.5%.");
            telemetryRepository.save(t1);

            SiloTelemetry t2 = new SiloTelemetry(warehouse, "Chamber B2 - Upper Plenum", 27.8, 62.0, 720.0);
            t2.setRiskLevel(SpoilageRiskLevel.OPTIMAL);
            t2.setAerationFanActive(false);
            t2.setRecommendation("OPTIMAL: Natural convection ventilation maintaining balanced temperature.");
            telemetryRepository.save(t2);
            log.info("Initialized sample IoT Silo Telemetry sensors");
        }

        log.info("Master data initialization completed successfully!");
    }

    private void createAccountIfAbsent(String code, String name, AccountType type, Double openingBalance) {
        if (accountRepository.findByAccountCode(code).isEmpty()) {
            Account acc = new Account(code, name, type, openingBalance);
            accountRepository.save(acc);
        }
    }

    private void createProductIfAbsent(String code, String name, GrainType grainType, GrainGrade grade, String unit, String desc) {
        if (productRepository.findByProductCode(code).isEmpty()) {
            Product prod = new Product(code, name, grainType, grade, unit, desc);
            productRepository.save(prod);
        }
    }

    private void createUserIfAbsent(String username, String password, String fullName, String email, Role role) {
        if (!userRepository.existsByUsername(username)) {
            User user = new User(username, password, fullName, email, role);
            userRepository.save(user);
        }
    }
}
