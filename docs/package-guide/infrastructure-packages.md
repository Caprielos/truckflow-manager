# Package infrastructure

Questa pagina elenca i package sotto `infrastructure` e il loro scopo.

## `infrastructure/memory`

Repository in memoria basati su Map/ConcurrentHashMap per provare il sistema senza database.

Classi principali:

- `InMemoryAuditTrailRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryContractRateCardRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryCustomerAccountRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryCustomerContractRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryCustomerRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryCustomerRevenueInvoiceRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryDeliveryNoteRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryDispatchPlanRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryDriverDefectTicketRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryDriverMissionPayrollRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryDriverMissionWorkReportRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryDriverPayrollPolicyRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryDriverRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryFacilityRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryFleetAssetAcquisitionRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryFleetAssetPurchaseRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryFleetFinancialStatementRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryFuelTransactionRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryGeneratedReportRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryImportBatchRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryInventoryBalanceRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryInventoryItemRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryInventoryStockMovementRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryLoadSecuringChecklistRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryMaintenanceWorkOrderRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryMissionEconomicsRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryNotificationMessageRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryParkingAssignmentRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryParkingSpotRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryRoutePlanRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryShipmentDocumentBundleRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryShipmentRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemorySupplierInvoiceRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryTelematicsSnapshotRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryTireInstallationRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryTireRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryTrackingTimelineRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryTransportClaimRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryTransportDocumentRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryTransportMissionRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryTransportOrderRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryUserAccountRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryVehicleCombinationRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryVehicleRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InMemoryWarehouseLocationRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
