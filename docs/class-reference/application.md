# Riferimento classi application

L’application layer contiene use case, porte e classi comuni.

| Package | Classe | Tipo | Cosa fa | Metodi principali |
| --- | --- | --- | --- | --- |
| application.common | ApplicationError | record | Errore applicativo strutturato usabile da controller, CLI o test. | ApplicationError, of |
| application.common | ApplicationResult | class | Risultato applicativo generico: incapsula un successo con valore o un fallimento con errori. | success, failure, isSuccess, isFailure, getValue, getValueOrThrow, getErrors |
| application.common | ResourceNotFoundException | class | Eccezione applicativa quando un repository non trova una risorsa richiesta. | - |
| application.port.in | AssignParkingSpotUseCase | interface | Porta di ingresso per assegnare una risorsa a un posto parcheggio. | - |
| application.port.in | CalculateDriverMissionPayrollUseCase | interface | Porta per calcolare il costo/stipendio autista da report lavoro e policy paghe. | - |
| application.port.in | CalculateMissionEconomicsUseCase | interface | Porta di ingresso per calcolare ricavi, costi e margine di una missione. | - |
| application.port.in | CloseTransportMissionUseCase | interface | Interfaccia di caso d’uso applicativo per l’azione CloseTransportMission. | - |
| application.port.in | CreateShipmentFromAcceptedOrderUseCase | interface | Interfaccia di caso d’uso applicativo per l’azione CreateShipmentFromAcceptedOrder. | - |
| application.port.in | GenerateShipmentDocumentBundleUseCase | interface | Interfaccia di caso d’uso applicativo per l’azione GenerateShipmentDocumentBundle. | - |
| application.port.in | PlanTransportMissionUseCase | interface | Porta di ingresso per pianificare una missione da spedizione, autista, convoglio e rotta. | - |
| application.port.in | RecordInventoryStockMovementUseCase | interface | Interfaccia di caso d’uso applicativo per l’azione RecordInventoryStockMovement. | - |
| application.port.in | RegisterCustomerContractUseCase | interface | Interfaccia di caso d’uso applicativo per l’azione RegisterCustomerContract. | - |
| application.port.in | RegisterFleetAssetAcquisitionUseCase | interface | Interfaccia di caso d’uso applicativo per l’azione RegisterFleetAssetAcquisition. | - |
| application.port.in | RegisterImportBatchUseCase | interface | Interfaccia di caso d’uso applicativo per l’azione RegisterImportBatch. | - |
| application.port.in | RegisterSupplierInvoiceUseCase | interface | Interfaccia di caso d’uso applicativo per l’azione RegisterSupplierInvoice. | - |
| application.port.out | AuditTrailRepository | interface | Porta repository per salvare/caricare oggetti AuditTrail senza dipendere dal database. | - |
| application.port.out | ContractRateCardRepository | interface | Porta repository per salvare/caricare oggetti ContractRateCard senza dipendere dal database. | - |
| application.port.out | CustomerAccountRepository | interface | Porta repository per salvare/caricare oggetti CustomerAccount senza dipendere dal database. | - |
| application.port.out | CustomerContractRepository | interface | Porta repository per salvare/caricare oggetti CustomerContract senza dipendere dal database. | - |
| application.port.out | CustomerRepository | interface | Porta repository per salvare/caricare oggetti Customer senza dipendere dal database. | - |
| application.port.out | CustomerRevenueInvoiceRepository | interface | Porta repository per salvare/caricare oggetti CustomerRevenueInvoice senza dipendere dal database. | - |
| application.port.out | DeliveryNoteRepository | interface | Porta repository per salvare/caricare oggetti DeliveryNote senza dipendere dal database. | - |
| application.port.out | DispatchPlanRepository | interface | Porta repository per salvare/caricare oggetti DispatchPlan senza dipendere dal database. | - |
| application.port.out | DriverDefectTicketRepository | interface | Porta repository per salvare/caricare oggetti DriverDefectTicket senza dipendere dal database. | - |
| application.port.out | DriverMissionPayrollRepository | interface | Porta repository per salvare/caricare oggetti DriverMissionPayroll senza dipendere dal database. | - |
| application.port.out | DriverMissionWorkReportRepository | interface | Porta repository per salvare/caricare oggetti DriverMissionWorkReport senza dipendere dal database. | - |
| application.port.out | DriverPayrollPolicyRepository | interface | Porta repository per salvare/caricare oggetti DriverPayrollPolicy senza dipendere dal database. | - |
| application.port.out | DriverRepository | interface | Porta repository per salvare/caricare oggetti Driver senza dipendere dal database. | - |
| application.port.out | FacilityRepository | interface | Porta repository per salvare/caricare oggetti Facility senza dipendere dal database. | - |
| application.port.out | FleetAssetAcquisitionRepository | interface | Porta repository per salvare/caricare oggetti FleetAssetAcquisition senza dipendere dal database. | - |
| application.port.out | FleetAssetPurchaseRepository | interface | Porta repository per salvare/caricare oggetti FleetAssetPurchase senza dipendere dal database. | - |
| application.port.out | FleetFinancialStatementRepository | interface | Porta repository per salvare/caricare oggetti FleetFinancialStatement senza dipendere dal database. | - |
| application.port.out | FuelTransactionRepository | interface | Porta repository per salvare/caricare oggetti FuelTransaction senza dipendere dal database. | - |
| application.port.out | GeneratedReportRepository | interface | Porta repository per salvare/caricare oggetti GeneratedReport senza dipendere dal database. | - |
| application.port.out | ImportBatchRepository | interface | Porta repository per salvare/caricare oggetti ImportBatch senza dipendere dal database. | - |
| application.port.out | InventoryBalanceRepository | interface | Porta repository per salvare/caricare oggetti InventoryBalance senza dipendere dal database. | - |
| application.port.out | InventoryItemRepository | interface | Porta repository per salvare/caricare oggetti InventoryItem senza dipendere dal database. | - |
| application.port.out | InventoryStockMovementRepository | interface | Porta repository per salvare/caricare oggetti InventoryStockMovement senza dipendere dal database. | - |
| application.port.out | LoadSecuringChecklistRepository | interface | Porta repository per salvare/caricare oggetti LoadSecuringChecklist senza dipendere dal database. | - |
| application.port.out | MaintenanceWorkOrderRepository | interface | Porta repository per salvare/caricare oggetti MaintenanceWorkOrder senza dipendere dal database. | - |
| application.port.out | MissionEconomicsRepository | interface | Porta repository per salvare/caricare oggetti MissionEconomics senza dipendere dal database. | - |
| application.port.out | NotificationMessageRepository | interface | Porta repository per salvare/caricare oggetti NotificationMessage senza dipendere dal database. | - |
| application.port.out | ParkingAssignmentRepository | interface | Porta repository per salvare/caricare oggetti ParkingAssignment senza dipendere dal database. | - |
| application.port.out | ParkingSpotRepository | interface | Porta repository per salvare/caricare oggetti ParkingSpot senza dipendere dal database. | - |
| application.port.out | RepositoryPort | interface | Interfaccia base dei repository: findById, save, findAll e getRequired. | - |
| application.port.out | RoutePlanRepository | interface | Porta repository per salvare/caricare oggetti RoutePlan senza dipendere dal database. | - |
| application.port.out | ShipmentDocumentBundleRepository | interface | Porta repository per salvare/caricare oggetti ShipmentDocumentBundle senza dipendere dal database. | - |
| application.port.out | ShipmentRepository | interface | Porta repository per salvare/caricare oggetti Shipment senza dipendere dal database. | - |
| application.port.out | SupplierInvoiceRepository | interface | Porta repository per salvare/caricare oggetti SupplierInvoice senza dipendere dal database. | - |
| application.port.out | TelematicsSnapshotRepository | interface | Porta repository per salvare/caricare oggetti TelematicsSnapshot senza dipendere dal database. | - |
| application.port.out | TireInstallationRepository | interface | Porta repository per salvare/caricare oggetti TireInstallation senza dipendere dal database. | - |
| application.port.out | TireRepository | interface | Porta repository per salvare/caricare oggetti Tire senza dipendere dal database. | - |
| application.port.out | TrackingTimelineRepository | interface | Porta repository per salvare/caricare oggetti TrackingTimeline senza dipendere dal database. | - |
| application.port.out | TransportClaimRepository | interface | Porta repository per salvare/caricare oggetti TransportClaim senza dipendere dal database. | - |
| application.port.out | TransportDocumentRepository | interface | Porta repository per salvare/caricare oggetti TransportDocument senza dipendere dal database. | - |
| application.port.out | TransportMissionRepository | interface | Porta repository per salvare/caricare oggetti TransportMission senza dipendere dal database. | - |
| application.port.out | TransportOrderRepository | interface | Porta repository per salvare/caricare oggetti TransportOrder senza dipendere dal database. | - |
| application.port.out | UserAccountRepository | interface | Porta repository per salvare/caricare oggetti UserAccount senza dipendere dal database. | - |
| application.port.out | VehicleCombinationRepository | interface | Porta repository per salvare/caricare oggetti VehicleCombination senza dipendere dal database. | - |
| application.port.out | VehicleRepository | interface | Porta repository per salvare/caricare oggetti Vehicle senza dipendere dal database. | - |
| application.port.out | WarehouseLocationRepository | interface | Porta repository per salvare/caricare oggetti WarehouseLocation senza dipendere dal database. | - |
| application.usecase | DefaultAssignParkingSpotUseCase | class | Implementazione dello use case di parcheggio: carica lo spot, crea assegnazione e salva. | handle |
| application.usecase | DefaultCalculateDriverMissionPayrollUseCase | class | Implementazione del calcolo payroll autista. | handle |
| application.usecase | DefaultCalculateMissionEconomicsUseCase | class | Implementazione del calcolo economics missione. | handle |
| application.usecase | DefaultCloseTransportMissionUseCase | class | Implementazione concreta dello use case CloseTransportMissionUseCase; coordina repository e domain. | handle |
| application.usecase | DefaultCreateShipmentFromAcceptedOrderUseCase | class | Implementazione concreta dello use case CreateShipmentFromAcceptedOrderUseCase; coordina repository e domain. | handle |
| application.usecase | DefaultGenerateShipmentDocumentBundleUseCase | class | Implementazione concreta dello use case GenerateShipmentDocumentBundleUseCase; coordina repository e domain. | handle |
| application.usecase | DefaultPlanTransportMissionUseCase | class | Implementazione della pianificazione missione: recupera dati, crea TransportMission e salva. | handle |
| application.usecase | DefaultRecordInventoryStockMovementUseCase | class | Implementazione concreta dello use case RecordInventoryStockMovementUseCase; coordina repository e domain. | handle |
| application.usecase | DefaultRegisterCustomerContractUseCase | class | Implementazione concreta dello use case RegisterCustomerContractUseCase; coordina repository e domain. | handle |
| application.usecase | DefaultRegisterFleetAssetAcquisitionUseCase | class | Implementazione concreta dello use case RegisterFleetAssetAcquisitionUseCase; coordina repository e domain. | handle |
| application.usecase | DefaultRegisterImportBatchUseCase | class | Implementazione concreta dello use case RegisterImportBatchUseCase; coordina repository e domain. | handle |
| application.usecase | DefaultRegisterSupplierInvoiceUseCase | class | Implementazione concreta dello use case RegisterSupplierInvoiceUseCase; coordina repository e domain. | handle |

## Ragionamento

- Le interfacce in `port/in` dicono cosa si può chiedere al sistema.
- Le interfacce in `port/out` dicono cosa serve all’application per lavorare.
- Le classi `Default...UseCase` sono implementazioni concrete.
