# Repository port

I repository port sono interfacce richieste dall'application layer. Non sono database: descrivono cosa serve caricare o salvare.

## Interfaccia base

```java
Optional<T> findById(String id);
void save(T aggregate);
List<T> findAll();
```

## Repository port presenti

- `AuditTrailRepository`
- `ContractRateCardRepository`
- `CustomerAccountRepository`
- `CustomerContractRepository`
- `CustomerRepository`
- `CustomerRevenueInvoiceRepository`
- `DeliveryNoteRepository`
- `DispatchPlanRepository`
- `DriverDefectTicketRepository`
- `DriverMissionPayrollRepository`
- `DriverMissionWorkReportRepository`
- `DriverPayrollPolicyRepository`
- `DriverRepository`
- `FacilityRepository`
- `FleetAssetAcquisitionRepository`
- `FleetAssetPurchaseRepository`
- `FleetFinancialStatementRepository`
- `FuelTransactionRepository`
- `GeneratedReportRepository`
- `ImportBatchRepository`
- `InventoryBalanceRepository`
- `InventoryItemRepository`
- `InventoryStockMovementRepository`
- `LoadSecuringChecklistRepository`
- `MaintenanceWorkOrderRepository`
- `MissionEconomicsRepository`
- `NotificationMessageRepository`
- `ParkingAssignmentRepository`
- `ParkingSpotRepository`
- `RepositoryPort`
- `RoutePlanRepository`
- `ShipmentDocumentBundleRepository`
- `ShipmentRepository`
- `SupplierInvoiceRepository`
- `TelematicsSnapshotRepository`
- `TireInstallationRepository`
- `TireRepository`
- `TrackingTimelineRepository`
- `TransportClaimRepository`
- `TransportDocumentRepository`
- `TransportMissionRepository`
- `TransportOrderRepository`
- `UserAccountRepository`
- `VehicleCombinationRepository`
- `VehicleRepository`
- `WarehouseLocationRepository`

## Perché sono utili

In futuro potrai avere implementazioni diverse:

```text
InMemoryVehicleRepository
JpaVehicleRepository
FileVehicleRepository
```

Gli use case non cambiano.
