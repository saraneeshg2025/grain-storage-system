# Digital Grain Storage & Quality Grading Management System

An enterprise-grade Agricultural Warehouse Management Backend for the **Public Distribution System (PDS)**, built with **Java 17/21**, **Spring Boot 3.x**, **Spring Data JPA/Hibernate**, and **MySQL**.

This system manages the complete lifecycle of food grains: from farmer procurement and automatic scientific quality grading, through warehouse capacity enforcement and inventory tracking, to PDS distribution, vendor billing, customer invoicing, double-entry accounting journals, and comprehensive executive reports.

---

## Key Highlights & Innovative Capabilities

1. **IoT Silo Micro-Climate Telemetry & Spoilage Prevention Engine (Innovative)**:
   - Real-time sensor telemetry ingestion for storage silos: **Core Temperature (°C)**, **Relative Humidity (%)**, and **CO2 Respiration (PPM)**.
   - Micro-climate analysis evaluates spoilage risk levels (**OPTIMAL**, **WARNING**, **CRITICAL**).
   - Automatically activates aeration fan triggers and recommends convection airflow cooling to prevent moisture condensation and mold infestation.
   - Predictive safe storage horizon calculation (estimated safe storage days remaining).

2. **Dynamic MSP & Quality Incentive / Dockage Matrix (Innovative)**:
   - Dynamic formula-driven procurement settlement conforming to Indian APMC Mandi & Public Distribution System (PDS) standards.
   - Grants **+2.5% Grade A Quality Incentive Bonus** for superior grain lots.
   - Implements automated dockage penalties for excess moisture (>12%) and foreign matter/impurities (>2%).
   - Calculates transparent net farmer payouts for **Direct Benefit Transfer (DBT)** disbursement.

3. **Digital Grain Quality Passport & Cryptographic Provenance (Innovative)**:
   - Generates an immutable **SHA-256 cryptographic batch hash** binding lot number, certified grade, mandi intake, assay metrics, and silo location.
   - Prevents grain adulteration, substitution, and black-market diversion within the PDS supply chain.
   - Records full custody milestones: *1. APMC Mandi Intake & Weighbridge* &rarr; *2. Scientific Quality Assay* &rarr; *3. Hermetic Silo Storage* &rarr; *4. Fair Price Shop Dispatch*.

4. **Rich Interactive Command Center Web UI (`/`)**:
   - Modern dark-mode glassmorphic dashboard built directly in Spring Boot (`src/main/resources/static/index.html`).
   - Visual Silo Tank fill level indicator with live capacity markers and aeration fan animation.
   - Real-time IoT sensor telemetry simulator with one-click test presets.
   - Interactive MSP & dockage calculator with instant DBT payout calculations.
   - Digital grain passport certificate viewer with tamper-evident milestone explorer.
   - Live double-entry financial & capacity report viewer.

5. **Automatic Scientific Quality Grading**:
   - Evaluates moisture and impurity levels.
   - Computes scientific quality score (0–100) and automatically assigns **GRADE_A**, **SUB_STANDARD**, or **REJECTED**.
   - Rejects food grains failing food safety parameters before entering silos.

6. **Strict Warehouse Capacity Validation**:
   - Real-time tracking of `totalCapacity`, `usedCapacity`, and `availableCapacity`.
   - Rejects incoming grain lots with HTTP 400 (`"Insufficient warehouse capacity"`) if available capacity is exceeded.
   - Automatically updates capacity upon procurement and distribution dispatch.

7. **FIFO Inventory & Double-Entry Bookkeeping**:
   - Tracks stock by warehouse, product, grain lot, and grade.
   - Every transaction generates balanced double-entry accounting journals (`totalDebit == totalCredit`).
   - Automated journals for Procurement, Payments, Sales Distribution, Customer Receipts, and Handling Expenses.
   - Financial statements: Balance Sheet, Profit & Loss, Budget Variance, and Capacity Reports.

8. **Postman Ready**:
   - Complete Postman collection included (`postman_collection.json`) covering all 17 REST controllers.

---

## Project Architecture & Package Structure

```
com.example.grainstorage
│
├── config/
│   ├── CorsConfig.java           # Cross-Origin Resource Sharing configuration
│   └── DataInitializer.java     # Preloads sample master data, accounts, silos & products
│
├── controller/                   # REST API Layer (DTO validation, HTTP responses)
│   ├── WarehouseController.java
│   ├── ContactController.java
│   ├── ProductController.java
│   ├── AccountController.java
│   ├── JournalController.java
│   ├── GrainLotController.java
│   ├── PurchaseOrderController.java
│   ├── VendorBillController.java
│   ├── InventoryController.java
│   ├── SalesOrderController.java
│   ├── CustomerInvoiceController.java
│   ├── PaymentController.java
│   ├── BudgetController.java
│   └── ReportController.java
│
├── dto/
│   ├── request/                  # Strongly-typed input DTOs with Jakarta Bean Validation
│   └── response/                 # Clean, non-circular response DTOs for reports & status
│
├── entity/                       # JPA Database Entities
│   ├── enums/                    # Business enums (WarehouseStatus, GrainGrade, etc.)
│   ├── Warehouse.java
│   ├── Contact.java
│   ├── Product.java
│   ├── Account.java
│   ├── Journal.java
│   ├── JournalEntry.java
│   ├── GrainLot.java
│   ├── PurchaseOrder.java
│   ├── PurchaseOrderItem.java
│   ├── VendorBill.java
│   ├── Inventory.java
│   ├── SalesOrder.java
│   ├── SalesOrderItem.java
│   ├── CustomerInvoice.java
│   ├── Payment.java
│   ├── Budget.java
│   └── User.java
│
├── exception/                    # Global Exception Handling
│   ├── ResourceNotFoundException.java
│   ├── InsufficientCapacityException.java
│   ├── InsufficientInventoryException.java
│   ├── InvalidTransactionException.java
│   └── GlobalExceptionHandler.java
│
├── repository/                   # Spring Data JPA Repositories
│   └── ...
│
├── service/                      # Core Business Logic Layer
│   ├── WarehouseService.java
│   ├── ContactService.java
│   ├── ProductService.java
│   ├── AccountService.java
│   ├── AccountingService.java
│   ├── QualityGradingService.java
│   ├── GrainLotService.java
│   ├── PurchaseOrderService.java
│   ├── VendorBillService.java
│   ├── InventoryService.java
│   ├── SalesOrderService.java
│   ├── CustomerInvoiceService.java
│   ├── PaymentService.java
│   ├── BudgetService.java
│   └── ReportService.java
│
└── GrainStorageApplication.java  # Spring Boot Main Entry Point
```

---

## Database Setup (MySQL)

Open your MySQL terminal or MySQL Workbench and run:

```sql
CREATE DATABASE IF NOT EXISTS grain_storage_db;
USE grain_storage_db;
```

Update your database credentials in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/grain_storage_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> **Note for Instant Testing / Offline Viva Demos**:  
> You can also run the application using the in-memory H2 profile without starting MySQL:
> ```bash
> mvn spring-boot:run -Dspring-boot.run.profiles=h2
> ```

---

## How to Run the Application

### Option 1: Using VS Code
1. Open the project folder `grain-storage-system` in VS Code (`File` -> `Open Folder...`).
2. Ensure the **Extension Pack for Java** and **Spring Boot Extension Pack** are installed.
3. Open `src/main/java/com/example/grainstorage/GrainStorageApplication.java` and click **Run** or press `F5`.

### Option 2: Using IntelliJ IDEA
1. Open IntelliJ IDEA -> `Open` -> select the `grain-storage-system` directory.
2. Select `pom.xml` as a Maven project.
3. Navigate to `src/main/java/com/example/grainstorage/GrainStorageApplication.java`.
4. Click the green **Run** arrow next to the `main` method.

### Option 3: Using Maven Command Line
```bash
# Build the project
mvn clean package -DskipTests

# Run the Spring Boot application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

---

## Complete End-to-End Business Flow

```
[Farmer Cooperative] ────────> Purchase Order
          │
          ▼
   Arrival of Grain Lot
          │
          ▼
[Quality Grading Service] ───> Checks Moisture & Impurity
          │
          ├───────────> [Rejected] ──> Exits Flow (No storage)
          │
          ▼ [Accepted]
[Warehouse Capacity Check] ──> If Full: Returns HTTP 400 "Insufficient warehouse capacity"
          │
          ▼ [Sufficient Capacity]
- Increases Warehouse Used Capacity
- Adds Lot to Live Inventory
- Generates Vendor Bill
- Posts Procurement Journal (Debit Inventory, Credit Creditors)
          │
          ▼
[Payment to Farmer] ─────────> Posts Payment Journal (Debit Creditors, Credit Bank)
          │
          ▼
[Sales Order to PDS Agency] ─> Validates Stock & Dispatches
          │
          ▼
- Reduces Live Inventory
- Decreases Warehouse Used Capacity (Frees Storage)
- Generates Customer Invoice
- Posts Sales Journal (Debit Customer Receivable, Credit Sales)
          │
          ▼
[Payment from PDS Agency] ───> Posts Receipt Journal (Debit Bank, Credit Customer Receivable)
          │
          ▼
[Executive Reporting] ───────> Balance Sheet, Profit & Loss, Budget Variance, Capacity Report
```

---

## REST API Reference & Sample Payloads

### 1. Warehouses (`/api/warehouses`)
- **POST `/api/warehouses`**: Create a new warehouse.
```json
{
  "warehouseCode": "WH-ZONE-4",
  "warehouseName": "Regional PDS Buffer Silo Zone 4",
  "location": "Amritsar Road, Punjab",
  "totalCapacity": 80000.0,
  "status": "ACTIVE"
}
```
- **GET `/api/warehouses`**: List all warehouses.
- **GET `/api/warehouses/{id}`**: Get warehouse details.
- **GET `/api/warehouses/{id}/capacity`**: Get real-time capacity and utilization %.

---

### 2. Contacts / Vendors / Customers (`/api/contacts`)
- **POST `/api/contacts`**: Register farmer cooperative or PDS customer agency.
```json
{
  "name": "Haryana Kisan Progressive Cooperative",
  "contactType": "FARMER_COOPERATIVE",
  "phone": "+91-9812345678",
  "email": "kisan.haryana@agricoop.org",
  "address": "Karnal Grain Market, Haryana"
}
```
- **GET `/api/contacts`**: List all contacts (optional `?contactType=FARMER_COOPERATIVE`).

---

### 3. Products (`/api/products`)
- **POST `/api/products`**: Create grain product.
```json
{
  "productCode": "RICE-BASMATI-A",
  "productName": "Basmati Paddy Lot Grade A",
  "grainType": "RICE",
  "defaultGrade": "GRADE_A",
  "unit": "KG",
  "description": "Aromatic long grain basmati rice lot"
}
```
- **GET `/api/products`**: List all grain products.

---

### 4. Chart of Accounts & Journals (`/api/accounts`, `/api/journals`)
- **POST `/api/accounts`**: Create custom ledger account.
- **POST `/api/journals`**: Post balanced double-entry manual journal.
```json
{
  "journalType": "EXPENSE",
  "description": "Warehouse fumigation and pest control",
  "referenceNumber": "EXP-FUM-101",
  "entries": [
    {
      "accountId": 8,
      "debit": 15000.0,
      "credit": 0.0,
      "description": "Storage handling expense"
    },
    {
      "accountId": 2,
      "debit": 0.0,
      "credit": 15000.0,
      "description": "Paid via Bank"
    }
  ]
}
```

---

### 5. Purchase Orders (`/api/purchase-orders`)
- **POST `/api/purchase-orders`**:
```json
{
  "poNumber": "PO-2026-001",
  "vendorId": 1,
  "warehouseId": 1,
  "items": [
    {
      "productId": 1,
      "quantity": 25000.0,
      "unitPrice": 24.50
    }
  ]
}
```
- **PUT `/api/purchase-orders/{id}/status?status=APPROVED`**: Approve PO.

---

### 6. Grain Procurement & Automatic Quality Grading (`/api/grain-lots`)
- **POST `/api/grain-lots`**: Procure grain lot and auto-grade:
```json
{
  "lotNumber": "LOT-WHEAT-2026-001",
  "productId": 1,
  "vendorId": 1,
  "warehouseId": 1,
  "quantity": 20000.0,
  "unitPrice": 24.50,
  "moisturePercentage": 11.2,
  "impurityPercentage": 1.4,
  "autoStore": false
}
```
- **POST `/api/grain-lots/{id}/grade`**: Re-evaluate quality with new lab results.
- **POST `/api/grain-lots/{id}/store`**:
  - Enforces capacity check (`quantity <= availableCapacity`).
  - Returns HTTP 400 `"Insufficient warehouse capacity"` if full.
  - Adds lot to inventory and updates capacity if successful.

---

### 7. Vendor Bills (`/api/vendor-bills`)
- **POST `/api/vendor-bills`**: Generates bill and creates procurement double-entry journal.
```json
{
  "billNumber": "BILL-2026-001",
  "purchaseOrderId": 1,
  "vendorId": 1,
  "amount": 490000.0
}
```

---

### 8. Payments (`/api/payments`)
- **POST `/api/payments`** (Disburse to farmer or receive from PDS agency):
```json
{
  "paymentNumber": "PAY-FRM-001",
  "partyId": 1,
  "amount": 490000.0,
  "paymentType": "BANK",
  "paymentCategory": "VENDOR_PAYMENT",
  "referenceNumber": "NEFT-SBI-987654",
  "vendorBillId": 1
}
```

---

### 9. Sales Orders & Distribution Dispatch (`/api/sales-orders`)
- **POST `/api/sales-orders`**: Create sales order for PDS agency.
```json
{
  "salesOrderNumber": "SO-PDS-2026-001",
  "customerId": 2,
  "warehouseId": 1,
  "items": [
    {
      "productId": 1,
      "quantity": 5000.0,
      "unitPrice": 28.00
    }
  ]
}
```
- **POST `/api/sales-orders/{id}/dispatch`**:
  - Validates stock availability.
  - Deducts stock from inventory (FIFO).
  - Frees warehouse used capacity.
  - Generates Customer Invoice.
  - Posts double-entry sales journal.

---

### 10. Budgets & Variance (`/api/budgets`)
- **POST `/api/budgets`**:
```json
{
  "budgetName": "Wheat Procurement Zone 3 Budget",
  "financialYear": "2026-27",
  "warehouseId": 1,
  "accountId": 7,
  "plannedAmount": 1500000.0
}
```
- **GET `/api/budgets/variance`**: View planned vs. actual expenditure and variance percentage.

---

### 11. Reports (`/api/reports`)
- `GET /api/reports/balance-sheet`: Double-entry balance sheet (Assets vs. Liabilities & Equity).
- `GET /api/reports/profit-loss`: Revenue from PDS dispatches vs. operational storage expenses.
- `GET /api/reports/budget`: Budget overview.
- `GET /api/reports/warehouse-capacity`: Live capacity matrix across all silos.
- `GET /api/reports/inventory`: Live inventory stock valuation and grade breakdown.

---

### 12. Silo IoT Micro-Climate Telemetry (`/api/telemetry`)
- **POST `/api/telemetry/record`**: Ingest live IoT sensor reading from a silo chamber.
```json
{
  "warehouseId": 1,
  "siloSection": "Chamber A1 - Silo Core",
  "temperatureCelsius": 25.5,
  "relativeHumidityPercentage": 58.0,
  "co2Ppm": 520.0
}
```
- **GET `/api/telemetry/warehouse/{id}`**: Historical sensor readings for a silo.
- **GET `/api/telemetry/warehouse/{id}/latest`**: Latest micro-climate state and risk level.
- **GET `/api/telemetry/alerts`**: Active spoilage risk alerts (`WARNING` or `CRITICAL`).

---

### 13. Dynamic MSP & Quality Incentive Matrix (`/api/pricing`)
- **POST `/api/pricing/calculate`**: Compute dynamic procurement price, bonuses, and dockage.
```json
{
  "grainType": "WHEAT",
  "quantityKg": 20000.0,
  "moisturePercentage": 11.2,
  "impurityPercentage": 1.4
}
```
*Returns: Quality score, Grade, Gross base amount, Grade A incentive bonus (+2.5%), Dockage deductions, Net farmer payable, and Direct Benefit Transfer (DBT) eligibility.*

---

### 14. Digital Grain Quality Passport & Cryptographic Provenance (`/api/provenance`)
- **GET `/api/provenance/{lotNumber}`**: Verify tamper-proof digital grain passport by lot number (e.g. `LOT-PDS-WHEAT-01`).
- **GET `/api/provenance/id/{lotId}`**: Verify passport by lot ID.
*Returns: SHA-256 cryptographic batch hash, safe hermetic storage days remaining, and step-by-step custody audit milestones.*

---

## Viva & Project Defense Guide (Common Questions)

1. **How is automatic quality grading implemented?**
   - Implemented in `QualityGradingService.java`. The system accepts moisture and impurity percentages. If moisture $\le 12\%$ and impurity $\le 2\%$, the grade is `GRADE_A`. If moisture $\le 15\%$ and impurity $\le 5\%$, it is `SUB_STANDARD`. Otherwise, it is marked `REJECTED` and rejected from being stored in the active warehouse.

2. **How does the system prevent warehouse overloading?**
   - In `WarehouseService.java`, before accepting or storing any grain lot, the system validates `incomingQuantity <= warehouse.availableCapacity`. If insufficient, an `InsufficientCapacityException` is thrown, which `GlobalExceptionHandler` converts into an HTTP 400 Bad Request with the message `"Insufficient warehouse capacity"`.

3. **How does double-entry accounting ensure financial consistency?**
   - In `AccountingService.java`, every journal transaction checks that `totalDebit == totalCredit`. For assets and expenses, debits increase balance and credits decrease balance. For liabilities and income, credits increase balance and debits decrease balance.

4. **What happens during grain dispatch?**
   - In `SalesOrderService.java` method `dispatchOrder()`, inventory is verified and deducted, warehouse used capacity is decreased (available capacity increases), a `CustomerInvoice` is generated, and a sales journal entry is posted automatically.

5. **What makes this project innovative compared to standard inventory software?**
   - **IoT Spoilage Prevention**: Analyzes multi-parameter micro-climate data (temperature, humidity, CO2 respiration) to prevent grain rotting before it occurs by automatically recommending aeration fan intervention.
   - **Cryptographic Provenance**: Employs SHA-256 batch signatures to create tamper-evident grain passports, preventing grain substitution or black-market diversion in the PDS supply chain.
   - **Dynamic MSP Pricing & DBT**: Transparently computes quality bonuses and moisture dockage so farmers are incentivized for superior grain quality while receiving direct payments.

