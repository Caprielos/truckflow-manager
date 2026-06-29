# Use case applicativi

Gli use case rappresentano azioni aziendali reali, non semplici CRUD su classi.

## Use case presenti

- `AssignParkingSpotUseCase`
- `CalculateDriverMissionPayrollUseCase`
- `CalculateMissionEconomicsUseCase`
- `CloseTransportMissionUseCase`
- `CreateShipmentFromAcceptedOrderUseCase`
- `GenerateShipmentDocumentBundleUseCase`
- `PlanTransportMissionUseCase`
- `RecordInventoryStockMovementUseCase`
- `RegisterCustomerContractUseCase`
- `RegisterFleetAssetAcquisitionUseCase`
- `RegisterImportBatchUseCase`
- `RegisterSupplierInvoiceUseCase`

## Implementazioni

- `DefaultAssignParkingSpotUseCase`
- `DefaultCalculateDriverMissionPayrollUseCase`
- `DefaultCalculateMissionEconomicsUseCase`
- `DefaultCloseTransportMissionUseCase`
- `DefaultCreateShipmentFromAcceptedOrderUseCase`
- `DefaultGenerateShipmentDocumentBundleUseCase`
- `DefaultPlanTransportMissionUseCase`
- `DefaultRecordInventoryStockMovementUseCase`
- `DefaultRegisterCustomerContractUseCase`
- `DefaultRegisterFleetAssetAcquisitionUseCase`
- `DefaultRegisterImportBatchUseCase`
- `DefaultRegisterSupplierInvoiceUseCase`

## Regola di progettazione

Non creare uno use case per ogni classe domain. Crea use case per flussi reali:

```text
pianificare una missione
calcolare economics
assegnare parcheggio
calcolare payroll
registrare magazzino
generare documenti
```
