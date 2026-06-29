# `infrastructure/memory`

Repository in memoria basati su Map/ConcurrentHashMap per provare il sistema senza database.

## Come leggerlo

Questo package contiene implementazioni tecniche. Può dipendere da application e domain.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `InMemoryAuditTrailRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryAuditTrailRepository |
| `InMemoryContractRateCardRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryContractRateCardRepository |
| `InMemoryCustomerAccountRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryCustomerAccountRepository |
| `InMemoryCustomerContractRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryCustomerContractRepository |
| `InMemoryCustomerRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryCustomerRepository |
| `InMemoryCustomerRevenueInvoiceRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryCustomerRevenueInvoiceRepository |
| `InMemoryDeliveryNoteRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryDeliveryNoteRepository |
| `InMemoryDispatchPlanRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryDispatchPlanRepository |
| `InMemoryDriverDefectTicketRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryDriverDefectTicketRepository |
| `InMemoryDriverMissionPayrollRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryDriverMissionPayrollRepository |
| `InMemoryDriverMissionWorkReportRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryDriverMissionWorkReportRepository |
| `InMemoryDriverPayrollPolicyRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryDriverPayrollPolicyRepository |
| `InMemoryDriverRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryDriverRepository |
| `InMemoryFacilityRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryFacilityRepository |
| `InMemoryFleetAssetAcquisitionRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryFleetAssetAcquisitionRepository |
| `InMemoryFleetAssetPurchaseRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryFleetAssetPurchaseRepository |
| `InMemoryFleetFinancialStatementRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryFleetFinancialStatementRepository |
| `InMemoryFuelTransactionRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryFuelTransactionRepository |
| `InMemoryGeneratedReportRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryGeneratedReportRepository |
| `InMemoryImportBatchRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryImportBatchRepository |
| `InMemoryInventoryBalanceRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryInventoryBalanceRepository |
| `InMemoryInventoryItemRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryInventoryItemRepository |
| `InMemoryInventoryStockMovementRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryInventoryStockMovementRepository |
| `InMemoryLoadSecuringChecklistRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryLoadSecuringChecklistRepository |
| `InMemoryMaintenanceWorkOrderRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryMaintenanceWorkOrderRepository |
| `InMemoryMissionEconomicsRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryMissionEconomicsRepository |
| `InMemoryNotificationMessageRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryNotificationMessageRepository |
| `InMemoryParkingAssignmentRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryParkingAssignmentRepository |
| `InMemoryParkingSpotRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryParkingSpotRepository, findById |
| `InMemoryRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | storage, idExtractor | findById, save, findAll, deleteById, clear |
| `InMemoryRoutePlanRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryRoutePlanRepository |
| `InMemoryShipmentDocumentBundleRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryShipmentDocumentBundleRepository |
| `InMemoryShipmentRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryShipmentRepository |
| `InMemorySupplierInvoiceRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemorySupplierInvoiceRepository |
| `InMemoryTelematicsSnapshotRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryTelematicsSnapshotRepository |
| `InMemoryTireInstallationRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryTireInstallationRepository |
| `InMemoryTireRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryTireRepository |
| `InMemoryTrackingTimelineRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryTrackingTimelineRepository |
| `InMemoryTransportClaimRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryTransportClaimRepository |
| `InMemoryTransportDocumentRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryTransportDocumentRepository |
| `InMemoryTransportMissionRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryTransportMissionRepository |
| `InMemoryTransportOrderRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryTransportOrderRepository |
| `InMemoryUserAccountRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryUserAccountRepository |
| `InMemoryVehicleCombinationRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryVehicleCombinationRepository |
| `InMemoryVehicleRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryVehicleRepository |
| `InMemoryWarehouseLocationRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | InMemoryWarehouseLocationRepository |
