# Architettura

TruckFlow Manager segue un’impostazione ispirata a clean architecture / hexagonal architecture.

## Livelli previsti

```text
src/main/java/it/gabriele/truckflow
├── domain
├── application
├── infrastructure
└── web
```

Attualmente il progetto è concentrato sul package `domain`.

## Domain

Il domain contiene:

- entity;
- value object;
- enum;
- rules;
- stati e transizioni;
- validazioni di business.

Non deve dipendere da:

- Spring;
- JPA;
- database;
- filesystem;
- controller REST;
- API esterne;
- UI.

## Application

L’application layer conterrà i casi d’uso. Esempi:

```text
CreateTransportOrderUseCase
AssignVehicleCombinationUseCase
RegisterFuelTransactionUseCase
ScheduleMaintenanceUseCase
```

L’application userà interfacce repository, per esempio:

```text
TransportOrderRepository
VehicleRepository
DriverRepository
```

## Infrastructure

Infrastructure conterrà implementazioni concrete:

```text
InMemoryTransportOrderRepository
JpaVehicleRepository
FileDocumentStorage
GpsProviderClient
FuelCardImportAdapter
```

## Web

Web arriverà dopo e conterrà controller/API/UI.

## Regola fondamentale

Il domain non sa come viene salvato o mostrato un dato. Sa solo cosa è valido e cosa non è valido secondo le regole del business.
