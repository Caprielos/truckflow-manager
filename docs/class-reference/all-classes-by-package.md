# Tutte le classi per package

Elenco generato dalla struttura attuale del progetto.

## `(root)`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `Main` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `Main.java` |

## `application/common`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `ApplicationError` | record | Record: piccolo oggetto immutabile usato per trasportare dati in modo compatto. | `application/common/ApplicationError.java` |
| `ApplicationResult` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `application/common/ApplicationResult.java` |
| `ResourceNotFoundException` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `application/common/ResourceNotFoundException.java` |

## `application/port`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `AssignParkingSpotUseCase` | interface | Use case port/in: contratto dell’azione applicativa esposta al mondo esterno. | `application/port/in/AssignParkingSpotUseCase.java` |
| `AuditTrailRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/AuditTrailRepository.java` |
| `CalculateDriverMissionPayrollUseCase` | interface | Use case port/in: contratto dell’azione applicativa esposta al mondo esterno. | `application/port/in/CalculateDriverMissionPayrollUseCase.java` |
| `CalculateMissionEconomicsUseCase` | interface | Use case port/in: contratto dell’azione applicativa esposta al mondo esterno. | `application/port/in/CalculateMissionEconomicsUseCase.java` |
| `CloseTransportMissionUseCase` | interface | Use case port/in: contratto dell’azione applicativa esposta al mondo esterno. | `application/port/in/CloseTransportMissionUseCase.java` |
| `ContractRateCardRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/ContractRateCardRepository.java` |
| `CreateShipmentFromAcceptedOrderUseCase` | interface | Use case port/in: contratto dell’azione applicativa esposta al mondo esterno. | `application/port/in/CreateShipmentFromAcceptedOrderUseCase.java` |
| `CustomerAccountRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/CustomerAccountRepository.java` |
| `CustomerContractRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/CustomerContractRepository.java` |
| `CustomerRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/CustomerRepository.java` |
| `CustomerRevenueInvoiceRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/CustomerRevenueInvoiceRepository.java` |
| `DeliveryNoteRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/DeliveryNoteRepository.java` |
| `DispatchPlanRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/DispatchPlanRepository.java` |
| `DriverDefectTicketRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/DriverDefectTicketRepository.java` |
| `DriverMissionPayrollRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/DriverMissionPayrollRepository.java` |
| `DriverMissionWorkReportRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/DriverMissionWorkReportRepository.java` |
| `DriverPayrollPolicyRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/DriverPayrollPolicyRepository.java` |
| `DriverRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/DriverRepository.java` |
| `FacilityRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/FacilityRepository.java` |
| `FleetAssetAcquisitionRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/FleetAssetAcquisitionRepository.java` |
| `FleetAssetPurchaseRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/FleetAssetPurchaseRepository.java` |
| `FleetFinancialStatementRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/FleetFinancialStatementRepository.java` |
| `FuelTransactionRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/FuelTransactionRepository.java` |
| `GenerateShipmentDocumentBundleUseCase` | interface | Use case port/in: contratto dell’azione applicativa esposta al mondo esterno. | `application/port/in/GenerateShipmentDocumentBundleUseCase.java` |
| `GeneratedReportRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/GeneratedReportRepository.java` |
| `ImportBatchRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/ImportBatchRepository.java` |
| `InventoryBalanceRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/InventoryBalanceRepository.java` |
| `InventoryItemRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/InventoryItemRepository.java` |
| `InventoryStockMovementRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/InventoryStockMovementRepository.java` |
| `LoadSecuringChecklistRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/LoadSecuringChecklistRepository.java` |
| `MaintenanceWorkOrderRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/MaintenanceWorkOrderRepository.java` |
| `MissionEconomicsRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/MissionEconomicsRepository.java` |
| `NotificationMessageRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/NotificationMessageRepository.java` |
| `ParkingAssignmentRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/ParkingAssignmentRepository.java` |
| `ParkingSpotRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/ParkingSpotRepository.java` |
| `PlanTransportMissionUseCase` | interface | Use case port/in: contratto dell’azione applicativa esposta al mondo esterno. | `application/port/in/PlanTransportMissionUseCase.java` |
| `RecordInventoryStockMovementUseCase` | interface | Use case port/in: contratto dell’azione applicativa esposta al mondo esterno. | `application/port/in/RecordInventoryStockMovementUseCase.java` |
| `RegisterCustomerContractUseCase` | interface | Use case port/in: contratto dell’azione applicativa esposta al mondo esterno. | `application/port/in/RegisterCustomerContractUseCase.java` |
| `RegisterFleetAssetAcquisitionUseCase` | interface | Use case port/in: contratto dell’azione applicativa esposta al mondo esterno. | `application/port/in/RegisterFleetAssetAcquisitionUseCase.java` |
| `RegisterImportBatchUseCase` | interface | Use case port/in: contratto dell’azione applicativa esposta al mondo esterno. | `application/port/in/RegisterImportBatchUseCase.java` |
| `RegisterSupplierInvoiceUseCase` | interface | Use case port/in: contratto dell’azione applicativa esposta al mondo esterno. | `application/port/in/RegisterSupplierInvoiceUseCase.java` |
| `RepositoryPort` | interface | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `application/port/out/RepositoryPort.java` |
| `RoutePlanRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/RoutePlanRepository.java` |
| `ShipmentDocumentBundleRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/ShipmentDocumentBundleRepository.java` |
| `ShipmentRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/ShipmentRepository.java` |
| `SupplierInvoiceRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/SupplierInvoiceRepository.java` |
| `TelematicsSnapshotRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/TelematicsSnapshotRepository.java` |
| `TireInstallationRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/TireInstallationRepository.java` |
| `TireRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/TireRepository.java` |
| `TrackingTimelineRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/TrackingTimelineRepository.java` |
| `TransportClaimRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/TransportClaimRepository.java` |
| `TransportDocumentRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/TransportDocumentRepository.java` |
| `TransportMissionRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/TransportMissionRepository.java` |
| `TransportOrderRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/TransportOrderRepository.java` |
| `UserAccountRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/UserAccountRepository.java` |
| `VehicleCombinationRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/VehicleCombinationRepository.java` |
| `VehicleRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/VehicleRepository.java` |
| `WarehouseLocationRepository` | interface | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `application/port/out/WarehouseLocationRepository.java` |

## `application/usecase`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `DefaultAssignParkingSpotUseCase` | class | Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale. | `application/usecase/DefaultAssignParkingSpotUseCase.java` |
| `DefaultCalculateDriverMissionPayrollUseCase` | class | Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale. | `application/usecase/DefaultCalculateDriverMissionPayrollUseCase.java` |
| `DefaultCalculateMissionEconomicsUseCase` | class | Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale. | `application/usecase/DefaultCalculateMissionEconomicsUseCase.java` |
| `DefaultCloseTransportMissionUseCase` | class | Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale. | `application/usecase/DefaultCloseTransportMissionUseCase.java` |
| `DefaultCreateShipmentFromAcceptedOrderUseCase` | class | Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale. | `application/usecase/DefaultCreateShipmentFromAcceptedOrderUseCase.java` |
| `DefaultGenerateShipmentDocumentBundleUseCase` | class | Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale. | `application/usecase/DefaultGenerateShipmentDocumentBundleUseCase.java` |
| `DefaultPlanTransportMissionUseCase` | class | Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale. | `application/usecase/DefaultPlanTransportMissionUseCase.java` |
| `DefaultRecordInventoryStockMovementUseCase` | class | Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale. | `application/usecase/DefaultRecordInventoryStockMovementUseCase.java` |
| `DefaultRegisterCustomerContractUseCase` | class | Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale. | `application/usecase/DefaultRegisterCustomerContractUseCase.java` |
| `DefaultRegisterFleetAssetAcquisitionUseCase` | class | Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale. | `application/usecase/DefaultRegisterFleetAssetAcquisitionUseCase.java` |
| `DefaultRegisterImportBatchUseCase` | class | Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale. | `application/usecase/DefaultRegisterImportBatchUseCase.java` |
| `DefaultRegisterSupplierInvoiceUseCase` | class | Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale. | `application/usecase/DefaultRegisterSupplierInvoiceUseCase.java` |

## `domain/audit`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `AuditActionType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/audit/AuditActionType.java` |
| `AuditActorType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/audit/AuditActorType.java` |
| `AuditEvent` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/audit/AuditEvent.java` |
| `AuditRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/audit/AuditRules.java` |
| `AuditSeverity` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/audit/AuditSeverity.java` |
| `AuditTrail` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/audit/AuditTrail.java` |

## `domain/availability`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `AvailabilityResourceType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/availability/AvailabilityResourceType.java` |
| `AvailabilityRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/availability/AvailabilityRules.java` |
| `AvailabilityStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/availability/AvailabilityStatus.java` |
| `ResourceAvailability` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/availability/ResourceAvailability.java` |

## `domain/billing`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `BillingRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/billing/BillingRules.java` |
| `Invoice` | class | Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini. | `domain/billing/Invoice.java` |
| `InvoiceStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/billing/InvoiceStatus.java` |
| `PaymentMethod` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/billing/PaymentMethod.java` |
| `PaymentRecord` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/billing/PaymentRecord.java` |

## `domain/cargo`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `AdrClass` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/cargo/AdrClass.java` |
| `CargoCategory` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/cargo/CargoCategory.java` |
| `CargoItem` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/cargo/CargoItem.java` |
| `CargoLoad` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/cargo/CargoLoad.java` |
| `CargoLoadRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/cargo/CargoLoadRules.java` |
| `CargoOperationalRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/cargo/CargoOperationalRules.java` |
| `DangerousGoodsProfile` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/cargo/DangerousGoodsProfile.java` |
| `HazardLabel` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/cargo/HazardLabel.java` |
| `PackingGroup` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/cargo/PackingGroup.java` |

## `domain/claim`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `ClaimRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/claim/ClaimRules.java` |
| `ClaimSeverity` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/claim/ClaimSeverity.java` |
| `ClaimStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/claim/ClaimStatus.java` |
| `ClaimType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/claim/ClaimType.java` |
| `DamageInspection` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/claim/DamageInspection.java` |
| `DamageInspectionItem` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/claim/DamageInspectionItem.java` |
| `TransportClaim` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/claim/TransportClaim.java` |

## `domain/company`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `CompanyComplianceRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/company/CompanyComplianceRules.java` |
| `CompanyLicense` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/company/CompanyLicense.java` |
| `CompanyLicenseType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/company/CompanyLicenseType.java` |
| `TransportCompany` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/company/TransportCompany.java` |

## `domain/compliance`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `ComplianceRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/compliance/ComplianceRules.java` |

## `domain/configuration`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `ConfigurationCategory` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/configuration/ConfigurationCategory.java` |
| `ConfigurationRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/configuration/ConfigurationRules.java` |
| `ConfigurationScope` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/configuration/ConfigurationScope.java` |
| `ConfigurationValue` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/configuration/ConfigurationValue.java` |
| `ConfigurationValueType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/configuration/ConfigurationValueType.java` |
| `SystemConfiguration` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/configuration/SystemConfiguration.java` |

## `domain/contract`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `ChargeUnit` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/contract/ChargeUnit.java` |
| `ContractRateCard` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/contract/ContractRateCard.java` |
| `CustomerContract` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/contract/CustomerContract.java` |
| `CustomerContractRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/contract/CustomerContractRules.java` |
| `TariffRule` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/contract/TariffRule.java` |
| `TariffRuleType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/contract/TariffRuleType.java` |

## `domain/customer`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `Customer` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/customer/Customer.java` |
| `CustomerAccount` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/customer/CustomerAccount.java` |
| `CustomerContact` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/customer/CustomerContact.java` |
| `CustomerContactRole` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/customer/CustomerContactRole.java` |
| `CustomerStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/customer/CustomerStatus.java` |
| `CustomerType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/customer/CustomerType.java` |

## `domain/dataimport`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `ExternalDataSourceType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/dataimport/ExternalDataSourceType.java` |
| `ImportBatch` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/dataimport/ImportBatch.java` |
| `ImportRecord` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/dataimport/ImportRecord.java` |
| `ImportRecordStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/dataimport/ImportRecordStatus.java` |
| `ImportRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/dataimport/ImportRules.java` |

## `domain/dispatch`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `DispatchAssignmentCandidate` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/dispatch/DispatchAssignmentCandidate.java` |
| `DispatchCheckResult` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/dispatch/DispatchCheckResult.java` |
| `DispatchCheckType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/dispatch/DispatchCheckType.java` |
| `DispatchPlan` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/dispatch/DispatchPlan.java` |
| `DispatchReadinessStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/dispatch/DispatchReadinessStatus.java` |
| `DispatchRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/dispatch/DispatchRules.java` |

## `domain/document`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `DeliveryNote` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/document/DeliveryNote.java` |
| `DeliveryNoteLine` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/document/DeliveryNoteLine.java` |
| `DocumentRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/document/DocumentRules.java` |
| `DocumentStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/document/DocumentStatus.java` |
| `ShipmentDocumentBundle` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/document/ShipmentDocumentBundle.java` |
| `TransportDocument` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/document/TransportDocument.java` |
| `TransportDocumentType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/document/TransportDocumentType.java` |

## `domain/driver`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `Driver` | class | Classe legata all’autista, alle sue abilitazioni o al suo costo operativo. | `domain/driver/Driver.java` |
| `DriverAdrCertificateType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/driver/DriverAdrCertificateType.java` |
| `DriverCertificate` | class | Classe legata all’autista, alle sue abilitazioni o al suo costo operativo. | `domain/driver/DriverCertificate.java` |
| `DriverCertificateType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/driver/DriverCertificateType.java` |
| `DriverLicenseCategory` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/driver/DriverLicenseCategory.java` |
| `DriverOperationalQualification` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/driver/DriverOperationalQualification.java` |
| `DriverProfessionalQualification` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/driver/DriverProfessionalQualification.java` |
| `DriverRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/driver/DriverRules.java` |
| `DriverStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/driver/DriverStatus.java` |

## `domain/drivetime`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `DriverTimeRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/drivetime/DriverTimeRules.java` |

## `domain/economics`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `CustomerRevenueInvoice` | class | Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini. | `domain/economics/CustomerRevenueInvoice.java` |
| `EconomicsRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/economics/EconomicsRules.java` |
| `FinancialBalance` | class | Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini. | `domain/economics/FinancialBalance.java` |
| `FinancingAgreement` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/economics/FinancingAgreement.java` |
| `FleetAssetAcquisition` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | `domain/economics/FleetAssetAcquisition.java` |
| `FleetAssetCategory` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/economics/FleetAssetCategory.java` |
| `FleetAssetCostComponent` | class | Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini. | `domain/economics/FleetAssetCostComponent.java` |
| `FleetAssetCostComponentType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/economics/FleetAssetCostComponentType.java` |
| `FleetAssetPurchase` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | `domain/economics/FleetAssetPurchase.java` |
| `FleetEconomicLedger` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | `domain/economics/FleetEconomicLedger.java` |
| `FleetFinancialStatement` | class | Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini. | `domain/economics/FleetFinancialStatement.java` |
| `InsurancePolicy` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/economics/InsurancePolicy.java` |
| `MissionCostLine` | class | Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini. | `domain/economics/MissionCostLine.java` |
| `MissionCostType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/economics/MissionCostType.java` |
| `MissionEconomics` | class | Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini. | `domain/economics/MissionEconomics.java` |
| `MissionRevenueLine` | class | Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini. | `domain/economics/MissionRevenueLine.java` |
| `MissionRevenueType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/economics/MissionRevenueType.java` |
| `ProfitabilityResult` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/economics/ProfitabilityResult.java` |
| `ProfitabilityStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/economics/ProfitabilityStatus.java` |
| `PurchaseCategory` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/economics/PurchaseCategory.java` |
| `PurchaseLine` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/economics/PurchaseLine.java` |
| `RecurringExpense` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/economics/RecurringExpense.java` |
| `RecurringExpenseCategory` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/economics/RecurringExpenseCategory.java` |
| `SupplierInvoice` | class | Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini. | `domain/economics/SupplierInvoice.java` |
| `SupplierInvoiceStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/economics/SupplierInvoiceStatus.java` |
| `TaxableRevenueLine` | class | Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini. | `domain/economics/TaxableRevenueLine.java` |
| `VatBreakdown` | class | Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini. | `domain/economics/VatBreakdown.java` |
| `VatRate` | class | Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini. | `domain/economics/VatRate.java` |
| `VatTreatment` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/economics/VatTreatment.java` |

## `domain/facility`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `Facility` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/facility/Facility.java` |
| `FacilityCostFrequency` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/facility/FacilityCostFrequency.java` |
| `FacilityCostLine` | class | Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini. | `domain/facility/FacilityCostLine.java` |
| `FacilityCostType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/facility/FacilityCostType.java` |
| `FacilityFinancialProfile` | class | Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini. | `domain/facility/FacilityFinancialProfile.java` |
| `FacilityOwnershipType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/facility/FacilityOwnershipType.java` |
| `FacilityType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/facility/FacilityType.java` |

## `domain/fleet`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `AxleSteeringType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/fleet/AxleSteeringType.java` |
| `BrakeSafetySystem` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/fleet/BrakeSafetySystem.java` |
| `BrakeType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/fleet/BrakeType.java` |
| `CouplingType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/fleet/CouplingType.java` |
| `DeadlineStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/fleet/DeadlineStatus.java` |
| `KingpinDiameter` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/fleet/KingpinDiameter.java` |
| `RetarderType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/fleet/RetarderType.java` |
| `SuspensionType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/fleet/SuspensionType.java` |
| `TireSpecification` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | `domain/fleet/TireSpecification.java` |
| `TransmissionType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/fleet/TransmissionType.java` |
| `Vehicle` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | `domain/fleet/Vehicle.java` |
| `VehicleAxle` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | `domain/fleet/VehicleAxle.java` |
| `VehicleAxleSpecification` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | `domain/fleet/VehicleAxleSpecification.java` |
| `VehicleBodyBaseType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/fleet/VehicleBodyBaseType.java` |
| `VehicleBodyCompatibilityRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/fleet/VehicleBodyCompatibilityRules.java` |
| `VehicleBodyConfiguration` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | `domain/fleet/VehicleBodyConfiguration.java` |
| `VehicleBodyType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/fleet/VehicleBodyType.java` |
| `VehicleCertificate` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | `domain/fleet/VehicleCertificate.java` |
| `VehicleCertificateType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/fleet/VehicleCertificateType.java` |
| `VehicleCombination` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | `domain/fleet/VehicleCombination.java` |
| `VehicleCombinationLegalLimitProfile` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | `domain/fleet/VehicleCombinationLegalLimitProfile.java` |
| `VehicleCombinationRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/fleet/VehicleCombinationRules.java` |
| `VehicleCombinationTechnicalRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/fleet/VehicleCombinationTechnicalRules.java` |
| `VehicleCombinationType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/fleet/VehicleCombinationType.java` |
| `VehicleCouplingSpecification` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | `domain/fleet/VehicleCouplingSpecification.java` |
| `VehicleDimensionSpecification` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | `domain/fleet/VehicleDimensionSpecification.java` |
| `VehicleEquipmentPosition` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/fleet/VehicleEquipmentPosition.java` |
| `VehicleLoadingEquipment` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | `domain/fleet/VehicleLoadingEquipment.java` |
| `VehicleLoadingEquipmentType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/fleet/VehicleLoadingEquipmentType.java` |
| `VehicleMassSpecification` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | `domain/fleet/VehicleMassSpecification.java` |
| `VehicleStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/fleet/VehicleStatus.java` |
| `VehicleTechnicalFeature` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/fleet/VehicleTechnicalFeature.java` |
| `VehicleTechnicalSpecification` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | `domain/fleet/VehicleTechnicalSpecification.java` |
| `VehicleType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/fleet/VehicleType.java` |
| `VehicleUnitType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/fleet/VehicleUnitType.java` |
| `VehicleWeightClass` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/fleet/VehicleWeightClass.java` |
| `WheelConfiguration` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/fleet/WheelConfiguration.java` |

## `domain/fuel`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `FuelCardProvider` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/fuel/FuelCardProvider.java` |
| `FuelConsumptionRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/fuel/FuelConsumptionRules.java` |
| `FuelTransaction` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/fuel/FuelTransaction.java` |

## `domain/identity`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `IdentityRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/identity/IdentityRules.java` |
| `UserAccount` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/identity/UserAccount.java` |
| `UserAccountStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/identity/UserAccountStatus.java` |
| `UserPermission` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/identity/UserPermission.java` |
| `UserRole` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/identity/UserRole.java` |

## `domain/inventory`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `InventoryBalance` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/inventory/InventoryBalance.java` |
| `InventoryItem` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/inventory/InventoryItem.java` |
| `InventoryItemType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/inventory/InventoryItemType.java` |
| `InventoryRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/inventory/InventoryRules.java` |
| `InventoryStockMovement` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/inventory/InventoryStockMovement.java` |
| `StockMovementType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/inventory/StockMovementType.java` |
| `WarehouseLocation` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/inventory/WarehouseLocation.java` |

## `domain/loadsecurity`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `LoadSecuringChecklist` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/loadsecurity/LoadSecuringChecklist.java` |
| `LoadSecuringEquipment` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/loadsecurity/LoadSecuringEquipment.java` |
| `LoadSecuringEquipmentType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/loadsecurity/LoadSecuringEquipmentType.java` |
| `LoadSecuringRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/loadsecurity/LoadSecuringRules.java` |

## `domain/location`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `Address` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/location/Address.java` |
| `GeoCoordinates` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/location/GeoCoordinates.java` |
| `Location` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/location/Location.java` |

## `domain/maintenance`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `DriverDefectTicket` | class | Classe legata all’autista, alle sue abilitazioni o al suo costo operativo. | `domain/maintenance/DriverDefectTicket.java` |
| `MaintenanceRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/maintenance/MaintenanceRules.java` |
| `MaintenanceStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/maintenance/MaintenanceStatus.java` |
| `MaintenanceType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/maintenance/MaintenanceType.java` |
| `MaintenanceWorkOrder` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/maintenance/MaintenanceWorkOrder.java` |
| `VehicleDowntime` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | `domain/maintenance/VehicleDowntime.java` |

## `domain/notification`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `NotificationChannel` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/notification/NotificationChannel.java` |
| `NotificationMessage` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/notification/NotificationMessage.java` |
| `NotificationPriority` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/notification/NotificationPriority.java` |
| `NotificationRecipientType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/notification/NotificationRecipientType.java` |
| `NotificationRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/notification/NotificationRules.java` |
| `NotificationStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/notification/NotificationStatus.java` |
| `NotificationType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/notification/NotificationType.java` |

## `domain/operation`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `TransportMission` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/operation/TransportMission.java` |
| `TransportMissionRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/operation/TransportMissionRules.java` |
| `TransportMissionStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/operation/TransportMissionStatus.java` |

## `domain/order`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `TransportOrder` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/order/TransportOrder.java` |
| `TransportOrderStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/order/TransportOrderStatus.java` |
| `TransportServiceType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/order/TransportServiceType.java` |

## `domain/parking`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `ParkedResource` | class | Classe parcheggio: modella posto, risorsa parcheggiata o assegnazione. | `domain/parking/ParkedResource.java` |
| `ParkingAssignment` | class | Classe parcheggio: modella posto, risorsa parcheggiata o assegnazione. | `domain/parking/ParkingAssignment.java` |
| `ParkingResourceType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/parking/ParkingResourceType.java` |
| `ParkingRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/parking/ParkingRules.java` |
| `ParkingSpot` | class | Classe parcheggio: modella posto, risorsa parcheggiata o assegnazione. | `domain/parking/ParkingSpot.java` |
| `ParkingSpotStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/parking/ParkingSpotStatus.java` |
| `ParkingSpotType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/parking/ParkingSpotType.java` |

## `domain/payroll`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `DriverMissionPayLine` | class | Classe legata all’autista, alle sue abilitazioni o al suo costo operativo. | `domain/payroll/DriverMissionPayLine.java` |
| `DriverMissionPayroll` | class | Classe legata all’autista, alle sue abilitazioni o al suo costo operativo. | `domain/payroll/DriverMissionPayroll.java` |
| `DriverMissionWorkReport` | class | Classe legata all’autista, alle sue abilitazioni o al suo costo operativo. | `domain/payroll/DriverMissionWorkReport.java` |
| `DriverPayComponentType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/payroll/DriverPayComponentType.java` |
| `DriverPayRule` | class | Classe legata all’autista, alle sue abilitazioni o al suo costo operativo. | `domain/payroll/DriverPayRule.java` |
| `DriverPayUnit` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/payroll/DriverPayUnit.java` |
| `DriverPayrollPolicy` | class | Classe legata all’autista, alle sue abilitazioni o al suo costo operativo. | `domain/payroll/DriverPayrollPolicy.java` |
| `DriverPayrollRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/payroll/DriverPayrollRules.java` |
| `MissionPayrollProjection` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/payroll/MissionPayrollProjection.java` |

## `domain/pricing`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `CostEstimationSource` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/pricing/CostEstimationSource.java` |
| `PriceBreakdown` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/pricing/PriceBreakdown.java` |
| `PricingLine` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/pricing/PricingLine.java` |
| `PricingLineType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/pricing/PricingLineType.java` |
| `PricingRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/pricing/PricingRules.java` |
| `RouteCostEstimate` | class | Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini. | `domain/pricing/RouteCostEstimate.java` |

## `domain/reporting`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `GeneratedReport` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/reporting/GeneratedReport.java` |
| `ReportDefinition` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/reporting/ReportDefinition.java` |
| `ReportFormat` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/reporting/ReportFormat.java` |
| `ReportMetric` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/reporting/ReportMetric.java` |
| `ReportMetricType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/reporting/ReportMetricType.java` |
| `ReportStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/reporting/ReportStatus.java` |
| `ReportType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/reporting/ReportType.java` |
| `ReportingRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/reporting/ReportingRules.java` |

## `domain/route`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `RoutePlan` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/route/RoutePlan.java` |
| `RoutePlanRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/route/RoutePlanRules.java` |
| `RouteStop` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/route/RouteStop.java` |
| `RouteStopType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/route/RouteStopType.java` |

## `domain/shared`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `DateRange` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/shared/DateRange.java` |
| `Dimension` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/shared/Dimension.java` |
| `Distance` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/shared/Distance.java` |
| `Money` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/shared/Money.java` |
| `Notes` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/shared/Notes.java` |
| `Percentage` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/shared/Percentage.java` |
| `TemperatureRange` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/shared/TemperatureRange.java` |
| `TimeWindow` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/shared/TimeWindow.java` |
| `Volume` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/shared/Volume.java` |
| `Weight` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/shared/Weight.java` |

## `domain/shipment`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `Shipment` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/shipment/Shipment.java` |
| `ShipmentRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/shipment/ShipmentRules.java` |
| `ShipmentStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/shipment/ShipmentStatus.java` |

## `domain/sustainability`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `EmissionEstimate` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/sustainability/EmissionEstimate.java` |
| `EmissionRating` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/sustainability/EmissionRating.java` |
| `EmissionStandard` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/sustainability/EmissionStandard.java` |
| `FuelType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/sustainability/FuelType.java` |
| `SustainabilityRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/sustainability/SustainabilityRules.java` |

## `domain/telematics`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `DrivingBehaviorEvent` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/telematics/DrivingBehaviorEvent.java` |
| `DrivingBehaviorEventType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/telematics/DrivingBehaviorEventType.java` |
| `TelematicsRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/telematics/TelematicsRules.java` |
| `TelematicsSnapshot` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/telematics/TelematicsSnapshot.java` |

## `domain/tire`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `Tire` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | `domain/tire/Tire.java` |
| `TireInstallation` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | `domain/tire/TireInstallation.java` |
| `TireRotationEvent` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | `domain/tire/TireRotationEvent.java` |
| `TireRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/tire/TireRules.java` |
| `TireStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/tire/TireStatus.java` |
| `WheelPosition` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/tire/WheelPosition.java` |
| `WheelSide` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/tire/WheelSide.java` |
| `WheelSlot` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/tire/WheelSlot.java` |

## `domain/tracking`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `TrackingEvent` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/tracking/TrackingEvent.java` |
| `TrackingEventType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | `domain/tracking/TrackingEventType.java` |
| `TrackingRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | `domain/tracking/TrackingRules.java` |
| `TrackingTimeline` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | `domain/tracking/TrackingTimeline.java` |

## `infrastructure/memory`

| Classe | Tipo | Scopo | File |
|---|---|---|---|
| `InMemoryAuditTrailRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryAuditTrailRepository.java` |
| `InMemoryContractRateCardRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryContractRateCardRepository.java` |
| `InMemoryCustomerAccountRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryCustomerAccountRepository.java` |
| `InMemoryCustomerContractRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryCustomerContractRepository.java` |
| `InMemoryCustomerRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryCustomerRepository.java` |
| `InMemoryCustomerRevenueInvoiceRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryCustomerRevenueInvoiceRepository.java` |
| `InMemoryDeliveryNoteRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryDeliveryNoteRepository.java` |
| `InMemoryDispatchPlanRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryDispatchPlanRepository.java` |
| `InMemoryDriverDefectTicketRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryDriverDefectTicketRepository.java` |
| `InMemoryDriverMissionPayrollRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryDriverMissionPayrollRepository.java` |
| `InMemoryDriverMissionWorkReportRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryDriverMissionWorkReportRepository.java` |
| `InMemoryDriverPayrollPolicyRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryDriverPayrollPolicyRepository.java` |
| `InMemoryDriverRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryDriverRepository.java` |
| `InMemoryFacilityRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryFacilityRepository.java` |
| `InMemoryFleetAssetAcquisitionRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryFleetAssetAcquisitionRepository.java` |
| `InMemoryFleetAssetPurchaseRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryFleetAssetPurchaseRepository.java` |
| `InMemoryFleetFinancialStatementRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryFleetFinancialStatementRepository.java` |
| `InMemoryFuelTransactionRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryFuelTransactionRepository.java` |
| `InMemoryGeneratedReportRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryGeneratedReportRepository.java` |
| `InMemoryImportBatchRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryImportBatchRepository.java` |
| `InMemoryInventoryBalanceRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryInventoryBalanceRepository.java` |
| `InMemoryInventoryItemRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryInventoryItemRepository.java` |
| `InMemoryInventoryStockMovementRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryInventoryStockMovementRepository.java` |
| `InMemoryLoadSecuringChecklistRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryLoadSecuringChecklistRepository.java` |
| `InMemoryMaintenanceWorkOrderRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryMaintenanceWorkOrderRepository.java` |
| `InMemoryMissionEconomicsRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryMissionEconomicsRepository.java` |
| `InMemoryNotificationMessageRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryNotificationMessageRepository.java` |
| `InMemoryParkingAssignmentRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryParkingAssignmentRepository.java` |
| `InMemoryParkingSpotRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryParkingSpotRepository.java` |
| `InMemoryRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryRepository.java` |
| `InMemoryRoutePlanRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryRoutePlanRepository.java` |
| `InMemoryShipmentDocumentBundleRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryShipmentDocumentBundleRepository.java` |
| `InMemoryShipmentRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryShipmentRepository.java` |
| `InMemorySupplierInvoiceRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemorySupplierInvoiceRepository.java` |
| `InMemoryTelematicsSnapshotRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryTelematicsSnapshotRepository.java` |
| `InMemoryTireInstallationRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryTireInstallationRepository.java` |
| `InMemoryTireRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryTireRepository.java` |
| `InMemoryTrackingTimelineRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryTrackingTimelineRepository.java` |
| `InMemoryTransportClaimRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryTransportClaimRepository.java` |
| `InMemoryTransportDocumentRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryTransportDocumentRepository.java` |
| `InMemoryTransportMissionRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryTransportMissionRepository.java` |
| `InMemoryTransportOrderRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryTransportOrderRepository.java` |
| `InMemoryUserAccountRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryUserAccountRepository.java` |
| `InMemoryVehicleCombinationRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryVehicleCombinationRepository.java` |
| `InMemoryVehicleRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryVehicleRepository.java` |
| `InMemoryWarehouseLocationRepository` | class | Repository port: interfaccia usata dall’application per caricare o salvare dati senza conoscere il database. | `infrastructure/memory/InMemoryWarehouseLocationRepository.java` |
