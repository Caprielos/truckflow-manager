# Package application

Questa pagina elenca i package sotto `application` e il loro scopo.

## `application/common`

Oggetti comuni dell’application layer: risultati applicativi, errori e eccezioni leggibili.

Classi principali:

- `ApplicationError` — Record: piccolo oggetto immutabile usato per trasportare dati in modo compatto.
- `ApplicationResult` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `ResourceNotFoundException` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

## `application/port`

Porte di ingresso e uscita: use case richiesti dall’esterno e repository richiesti dall’application.

Classi principali:

- `AssignParkingSpotUseCase` — Use case port/in: contratto dell’azione applicativa esposta al mondo esterno.
- `AuditTrailRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `CalculateDriverMissionPayrollUseCase` — Use case port/in: contratto dell’azione applicativa esposta al mondo esterno.
- `CalculateMissionEconomicsUseCase` — Use case port/in: contratto dell’azione applicativa esposta al mondo esterno.
- `CloseTransportMissionUseCase` — Use case port/in: contratto dell’azione applicativa esposta al mondo esterno.
- `ContractRateCardRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `CreateShipmentFromAcceptedOrderUseCase` — Use case port/in: contratto dell’azione applicativa esposta al mondo esterno.
- `CustomerAccountRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `CustomerContractRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `CustomerRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `CustomerRevenueInvoiceRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `DeliveryNoteRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `DispatchPlanRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `DriverDefectTicketRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `DriverMissionPayrollRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `DriverMissionWorkReportRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `DriverPayrollPolicyRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `DriverRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `FacilityRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `FleetAssetAcquisitionRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `FleetAssetPurchaseRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `FleetFinancialStatementRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `FuelTransactionRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `GenerateShipmentDocumentBundleUseCase` — Use case port/in: contratto dell’azione applicativa esposta al mondo esterno.
- `GeneratedReportRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `ImportBatchRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InventoryBalanceRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InventoryItemRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `InventoryStockMovementRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `LoadSecuringChecklistRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `MaintenanceWorkOrderRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `MissionEconomicsRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `NotificationMessageRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `ParkingAssignmentRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `ParkingSpotRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `PlanTransportMissionUseCase` — Use case port/in: contratto dell’azione applicativa esposta al mondo esterno.
- `RecordInventoryStockMovementUseCase` — Use case port/in: contratto dell’azione applicativa esposta al mondo esterno.
- `RegisterCustomerContractUseCase` — Use case port/in: contratto dell’azione applicativa esposta al mondo esterno.
- `RegisterFleetAssetAcquisitionUseCase` — Use case port/in: contratto dell’azione applicativa esposta al mondo esterno.
- `RegisterImportBatchUseCase` — Use case port/in: contratto dell’azione applicativa esposta al mondo esterno.
- `RegisterSupplierInvoiceUseCase` — Use case port/in: contratto dell’azione applicativa esposta al mondo esterno.
- `RepositoryPort` — Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.
- `RoutePlanRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `ShipmentDocumentBundleRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `ShipmentRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `SupplierInvoiceRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `TelematicsSnapshotRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `TireInstallationRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `TireRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `TrackingTimelineRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `TransportClaimRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `TransportDocumentRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `TransportMissionRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `TransportOrderRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `UserAccountRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `VehicleCombinationRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `VehicleRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.
- `WarehouseLocationRepository` — Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database.

## `application/usecase`

Implementazioni dei casi d’uso: coordinano domain e repository, senza conoscere database o web.

Classi principali:

- `DefaultAssignParkingSpotUseCase` — Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale.
- `DefaultCalculateDriverMissionPayrollUseCase` — Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale.
- `DefaultCalculateMissionEconomicsUseCase` — Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale.
- `DefaultCloseTransportMissionUseCase` — Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale.
- `DefaultCreateShipmentFromAcceptedOrderUseCase` — Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale.
- `DefaultGenerateShipmentDocumentBundleUseCase` — Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale.
- `DefaultPlanTransportMissionUseCase` — Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale.
- `DefaultRecordInventoryStockMovementUseCase` — Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale.
- `DefaultRegisterCustomerContractUseCase` — Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale.
- `DefaultRegisterFleetAssetAcquisitionUseCase` — Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale.
- `DefaultRegisterImportBatchUseCase` — Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale.
- `DefaultRegisterSupplierInvoiceUseCase` — Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale.
