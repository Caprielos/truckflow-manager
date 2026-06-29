# Implementation roadmap

## Stato attuale

Il domain layer è molto ricco. La prossima evoluzione non dovrebbe essere aggiungere altri enum a caso, ma costruire sopra al domain.

## Step 1 — Application layer

Creare package:

```text
src/main/java/it/gabriele/truckflow/application
```

Use case consigliati:

- `CreateTransportOrderUseCase`
- `CreateShipmentFromAcceptedOrderUseCase`
- `PlanTransportMissionUseCase`
- `CalculateMissionProfitabilityUseCase`
- `AssignParkingSpotUseCase`
- `RegisterFleetAssetAcquisitionUseCase`
- `RegisterDriverPayrollForMissionUseCase`
- `CloseMissionAndGenerateBillingUseCase`

## Step 2 — Repository ports

Creare interfacce:

```text
VehicleRepository
DriverRepository
ShipmentRepository
MissionRepository
CustomerRepository
EconomicsRepository
FacilityRepository
ParkingRepository
InventoryRepository
```

## Step 3 — Infrastructure memory

Implementazioni in memoria per demo e test:

```text
infrastructure/memory
```

## Step 4 — Web/API

Solo dopo application e repository:

```text
web/rest
```

Endpoint possibili:

```text
POST /orders
POST /shipments/{id}/plan-mission
POST /missions/{id}/close
GET /vehicles
GET /parking/spots
GET /economics/statement
GET /drivers/{id}/payroll
```

## Step 5 — Persistence

Database e JPA solo dopo avere use case e repository chiari.
