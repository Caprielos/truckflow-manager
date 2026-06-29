# `application/usecase`

Implementazioni dei casi d’uso: coordinano domain e repository, senza conoscere database o web.

## Come leggerlo

Questo package fa parte dell’application layer. Coordina azioni e dipendenze, ma non deve contenere dettagli di database o web.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `DefaultAssignParkingSpotUseCase` | class | Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale. | parkingSpotRepository, parkingAssignmentRepository | DefaultAssignParkingSpotUseCase, handle |
| `DefaultCalculateDriverMissionPayrollUseCase` | class | Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale. | workReportRepository, payrollPolicyRepository, payrollRepository | DefaultCalculateDriverMissionPayrollUseCase, handle |
| `DefaultCalculateMissionEconomicsUseCase` | class | Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale. | missionRepository, shipmentRepository, economicsRepository | DefaultCalculateMissionEconomicsUseCase, handle |
| `DefaultCloseTransportMissionUseCase` | class | Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale. | missionRepository | DefaultCloseTransportMissionUseCase, handle |
| `DefaultCreateShipmentFromAcceptedOrderUseCase` | class | Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale. | orderRepository, shipmentRepository | DefaultCreateShipmentFromAcceptedOrderUseCase, handle |
| `DefaultGenerateShipmentDocumentBundleUseCase` | class | Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale. | shipmentRepository, bundleRepository | DefaultGenerateShipmentDocumentBundleUseCase, handle |
| `DefaultPlanTransportMissionUseCase` | class | Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale. | shipmentRepository, driverRepository, vehicleCombinationRepository, routePlanRepository, missionRepository | DefaultPlanTransportMissionUseCase, handle |
| `DefaultRecordInventoryStockMovementUseCase` | class | Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale. | itemRepository, warehouseLocationRepository, movementRepository, balanceRepository | DefaultRecordInventoryStockMovementUseCase, handle |
| `DefaultRegisterCustomerContractUseCase` | class | Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale. | repository | DefaultRegisterCustomerContractUseCase, handle |
| `DefaultRegisterFleetAssetAcquisitionUseCase` | class | Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale. | repository | DefaultRegisterFleetAssetAcquisitionUseCase, handle |
| `DefaultRegisterImportBatchUseCase` | class | Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale. | repository | DefaultRegisterImportBatchUseCase, handle |
| `DefaultRegisterSupplierInvoiceUseCase` | class | Implementazione concreta di uno use case: coordina repository e domain per eseguire un’azione reale. | repository | DefaultRegisterSupplierInvoiceUseCase, handle |
