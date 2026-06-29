# Infrastructure memory

`infrastructure/memory` contiene repository concreti in memoria.

## Cosa significa in memoria

I dati vengono salvati in una `ConcurrentHashMap`. Non finiscono in un database e spariscono quando l'applicazione si chiude.

Questa soluzione è utile per:

- test di scenario;
- demo senza database;
- sviluppo iniziale;
- verificare application layer.

## Classe base

```text
InMemoryRepository<T>
```

Funzioni principali:

```text
findById
save
findAll
deleteById
clear
```

## Repository memory presenti

- `InMemoryAuditTrailRepository`
- `InMemoryContractRateCardRepository`
- `InMemoryCustomerAccountRepository`
- `InMemoryCustomerContractRepository`
- `InMemoryCustomerRepository`
- `InMemoryCustomerRevenueInvoiceRepository`
- `InMemoryDeliveryNoteRepository`
- `InMemoryDispatchPlanRepository`
- `InMemoryDriverDefectTicketRepository`
- `InMemoryDriverMissionPayrollRepository`
- `InMemoryDriverMissionWorkReportRepository`
- `InMemoryDriverPayrollPolicyRepository`
- `InMemoryDriverRepository`
- `InMemoryFacilityRepository`
- `InMemoryFleetAssetAcquisitionRepository`
- `InMemoryFleetAssetPurchaseRepository`
- `InMemoryFleetFinancialStatementRepository`
- `InMemoryFuelTransactionRepository`
- `InMemoryGeneratedReportRepository`
- `InMemoryImportBatchRepository`
- `InMemoryInventoryBalanceRepository`
- `InMemoryInventoryItemRepository`
- `InMemoryInventoryStockMovementRepository`
- `InMemoryLoadSecuringChecklistRepository`
- `InMemoryMaintenanceWorkOrderRepository`
- `InMemoryMissionEconomicsRepository`
- `InMemoryNotificationMessageRepository`
- `InMemoryParkingAssignmentRepository`
- `InMemoryParkingSpotRepository`
- `InMemoryRepository`
- `InMemoryRoutePlanRepository`
- `InMemoryShipmentDocumentBundleRepository`
- `InMemoryShipmentRepository`
- `InMemorySupplierInvoiceRepository`
- `InMemoryTelematicsSnapshotRepository`
- `InMemoryTireInstallationRepository`
- `InMemoryTireRepository`
- `InMemoryTrackingTimelineRepository`
- `InMemoryTransportClaimRepository`
- `InMemoryTransportDocumentRepository`
- `InMemoryTransportMissionRepository`
- `InMemoryTransportOrderRepository`
- `InMemoryUserAccountRepository`
- `InMemoryVehicleCombinationRepository`
- `InMemoryVehicleRepository`
- `InMemoryWarehouseLocationRepository`

## Limite importante

Non è persistenza reale. Per un prodotto finale servirà un database o altro storage persistente.
