# `application/port`

Porte di ingresso e uscita: use case richiesti dall’esterno e repository richiesti dall’application.

## Come leggerlo

Questo package fa parte dell’application layer. Coordina azioni e dipendenze, ma non deve contenere dettagli di database o web.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `AssignParkingSpotUseCase` | interface | Use case port/in: contratto dell’azione applicativa esposta al mondo esterno. | — | — |
| `AuditTrailRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `CalculateDriverMissionPayrollUseCase` | interface | Use case port/in: contratto dell’azione applicativa esposta al mondo esterno. | — | — |
| `CalculateMissionEconomicsUseCase` | interface | Use case port/in: contratto dell’azione applicativa esposta al mondo esterno. | — | — |
| `CloseTransportMissionUseCase` | interface | Use case port/in: contratto dell’azione applicativa esposta al mondo esterno. | — | — |
| `ContractRateCardRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `CreateShipmentFromAcceptedOrderUseCase` | interface | Use case port/in: contratto dell’azione applicativa esposta al mondo esterno. | — | — |
| `CustomerAccountRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `CustomerContractRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `CustomerRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `CustomerRevenueInvoiceRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `DeliveryNoteRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `DispatchPlanRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `DriverDefectTicketRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `DriverMissionPayrollRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `DriverMissionWorkReportRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `DriverPayrollPolicyRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `DriverRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `FacilityRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `FleetAssetAcquisitionRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `FleetAssetPurchaseRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `FleetFinancialStatementRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `FuelTransactionRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `GenerateShipmentDocumentBundleUseCase` | interface | Use case port/in: contratto dell’azione applicativa esposta al mondo esterno. | — | — |
| `GeneratedReportRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `ImportBatchRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `InventoryBalanceRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `InventoryItemRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `InventoryStockMovementRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `LoadSecuringChecklistRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `MaintenanceWorkOrderRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `MissionEconomicsRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `NotificationMessageRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `ParkingAssignmentRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `ParkingSpotRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `PlanTransportMissionUseCase` | interface | Use case port/in: contratto dell’azione applicativa esposta al mondo esterno. | — | — |
| `RecordInventoryStockMovementUseCase` | interface | Use case port/in: contratto dell’azione applicativa esposta al mondo esterno. | — | — |
| `RegisterCustomerContractUseCase` | interface | Use case port/in: contratto dell’azione applicativa esposta al mondo esterno. | — | — |
| `RegisterFleetAssetAcquisitionUseCase` | interface | Use case port/in: contratto dell’azione applicativa esposta al mondo esterno. | — | — |
| `RegisterImportBatchUseCase` | interface | Use case port/in: contratto dell’azione applicativa esposta al mondo esterno. | — | — |
| `RegisterSupplierInvoiceUseCase` | interface | Use case port/in: contratto dell’azione applicativa esposta al mondo esterno. | — | — |
| `RepositoryPort` | interface | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | — | — |
| `RoutePlanRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `ShipmentDocumentBundleRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `ShipmentRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `SupplierInvoiceRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `TelematicsSnapshotRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `TireInstallationRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `TireRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `TrackingTimelineRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `TransportClaimRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `TransportDocumentRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `TransportMissionRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `TransportOrderRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `UserAccountRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `VehicleCombinationRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `VehicleRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
| `WarehouseLocationRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | — | — |
