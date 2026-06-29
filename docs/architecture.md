# Architettura

L'architettura è organizzata in stile pulito/esagonale.

```text
web / CLI / test scenario
        ↓
application/port/in
        ↓
application/usecase
        ↓
domain
        ↓
application/port/out
        ↓
infrastructure/memory o futuro database
```

## Domain

Il `domain` contiene le regole del business. Non deve conoscere database, REST API, file, JSON o Spring.

Esempi:

```text
Vehicle
Driver
Shipment
TransportMission
MissionEconomics
DriverMissionPayroll
ParkingAssignment
InventoryBalance
```

## Application

L'`application` contiene le azioni reali del sistema.

Esempi:

```text
PlanTransportMissionUseCase
AssignParkingSpotUseCase
CalculateMissionEconomicsUseCase
CalculateDriverMissionPayrollUseCase
```

Non deve contenere dettagli di database. Coordina domain object e repository port.

## Infrastructure

L'`infrastructure` contiene implementazioni tecniche.

Per ora è presente:

```text
infrastructure/memory
```

Questa cartella contiene repository in memoria, utili per test, demo e sviluppo iniziale.

## Perché questa separazione

La separazione evita che il codice business dipenda da dettagli tecnici.

In futuro potrai sostituire:

```text
InMemoryVehicleRepository
```

con:

```text
JpaVehicleRepository
```

senza cambiare le regole del domain e senza riscrivere gli use case.
