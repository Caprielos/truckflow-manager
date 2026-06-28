# TruckFlow Manager

> Documentazione aggiornata e allineata al domain MVP implementato.

Package root del domain:

```text
it.gabriele.truckflow.domain
```

Regola principale:

```text
Il domain rappresenta il business.
La tecnologia serve solo a farlo funzionare.
```


# Architecture

## Scopo

Descrive l'architettura prevista del progetto e le regole per mantenere il domain indipendente.

## Architettura a livelli

```text
Frontend Web / UI
        ↓
REST API / Controller
        ↓
Application Layer / Use Case
        ↓
Domain
        ↓
Infrastructure
```

## Domain Layer

Contiene regole di business pure:

- entity;
- value object;
- enum;
- regole di dominio;
- transizioni di stato;
- validazioni.

Il domain NON dipende da:

- Spring;
- JPA;
- database;
- REST;
- frontend;
- filesystem;
- email;
- autenticazione tecnica;
- Google Maps;
- ViaMichelin;
- HERE;
- PTV;
- servizi esterni.

Package attuali:

`shared`, `cargo`, `location`, `facility`, `customer`, `order`, `shipment`, `route`, `fleet`, `driver`, `compliance`, `operation`, `availability`, `tracking`, `maintenance`, `pricing`, `billing`, `document`, `claim`, `audit`, `notification`, `sustainability`, `identity`, `configuration`, `reporting`

## Application Layer futuro

Coordinerà i casi d'uso.

Esempi:

```text
CreateTransportOrderUseCase
SubmitTransportOrderUseCase
AcceptTransportOrderUseCase
CreateShipmentFromOrderUseCase
PlanShipmentUseCase
CreateTransportMissionUseCase
DispatchMissionUseCase
CompleteMissionUseCase
GenerateInvoiceUseCase
RegisterPaymentUseCase
GenerateReportUseCase
```

L'application layer potrà usare porte/interfacce:

```text
TransportOrderRepository
ShipmentRepository
RouteCostEstimator
NotificationSender
DocumentStorage
AuditPublisher
```

## Infrastructure Layer futuro

Conterrà dettagli tecnici:

```text
PostgreSQL repositories
JPA mappings
ViaMichelin adapter
HERE/PTV/Google adapter
Email sender
Document storage
Security adapter
```

## Regola delle dipendenze

```text
Frontend → API → Application → Domain
Infrastructure → Application/Domain contracts
Domain → nulla di tecnico
```

Il domain non deve importare classi di Spring, JPA, controller, repository o client esterni.

## Scelta su ViaMichelin e provider esterni

Il domain può rappresentare una stima proveniente da `VIAMICHELIN`, `HERE_MAPS`, `PTV` o `GOOGLE_MAPS` tramite `CostEstimationSource`.

Il domain però non deve chiamare provider esterni.

Architettura corretta futura:

```text
application port: RouteCostEstimator
infrastructure adapter: ViaMichelinRouteCostEstimator
domain object: RouteCostEstimate
```
