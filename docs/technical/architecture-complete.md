# Architettura completa

TruckFlow Manager segue una struttura ispirata a Clean Architecture / Hexagonal Architecture.

## Regola principale delle dipendenze

```text
application dipende da domain
infrastructure dipende da application e domain
future web dipenderà da application

domain non dipende da nessuno strato esterno
```

## Perché è importante

Il domain deve poter essere testato senza database, senza web, senza Spring e senza file esterni. Questo rende le regole più pulite.

## Livelli

### domain

Contiene oggetti e regole del business. Esempio:

```text
Vehicle
Driver
Shipment
TransportMission
MissionEconomics
ParkingAssignment
```

### application

Contiene use case. Esempio:

```text
PlanTransportMissionUseCase
AssignParkingSpotUseCase
CalculateMissionEconomicsUseCase
```

### infrastructure/memory

Contiene repository in memoria. Esempio:

```text
InMemoryParkingSpotRepository
InMemoryTransportMissionRepository
```

### web futura

Conterrà REST controller. Esempio futuro:

```text
POST /parking/assignments
POST /missions/plan
GET /economics/statements
```

## Flusso tipico

```text
Controller futuro / Test scenario
        ↓
port/in UseCase
        ↓
application/usecase Service
        ↓
port/out Repository
        ↓
infrastructure/memory o database futuro
        ↓
domain object e domain rules
```
