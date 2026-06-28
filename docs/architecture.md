# Architecture

## Architettura prevista

Il progetto segue una separazione a strati:

```text
domain
application
infrastructure
web
```

## Domain

Il domain contiene il linguaggio e le regole del business.

Esempi:

- `CargoLoadRules`
- `VehicleBodyCompatibilityRules`
- `DriverRules`
- `CompanyComplianceRules`
- `DocumentRules`
- `FuelConsumptionRules`
- `TireRules`
- `TransportMissionRules`

Il domain è puro Java e non dipende da framework.

## Application

L’application layer sarà il prossimo passo.

Conterrà i casi d’uso:

- `CreateTransportOrderUseCase`
- `AcceptTransportOrderUseCase`
- `CreateShipmentFromOrderUseCase`
- `PlanTransportMissionUseCase`
- `AssignDriverAndVehicleUseCase`
- `RegisterFuelTransactionUseCase`
- `CreateMaintenanceWorkOrderUseCase`

Userà repository port, cioè interfacce, non implementazioni concrete.

## Infrastructure

L’infrastructure conterrà implementazioni tecniche:

- repository in memoria;
- repository database;
- integrazioni GPS;
- integrazioni carte carburante;
- integrazioni route cost;
- generazione documenti;
- email/notifiche esterne.

## Web

Il web layer arriverà dopo.

Esporrà API REST o UI, ma non deve contenere regole di business.

## Dipendenze corrette

```text
web -> application -> domain
infrastructure -> application/domain
domain -> nessuno strato esterno
```

Il domain non deve mai dipendere da application, infrastructure o web.
