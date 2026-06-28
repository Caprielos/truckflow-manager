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


# Implementation Roadmap

## Stato attuale

Il domain MVP è completato nella sua parte principale.

Package completati:

```text
├── shared
├── cargo
├── location
├── facility
├── customer
├── order
├── shipment
├── route
├── fleet
├── driver
├── compliance
├── operation
├── availability
├── tracking
├── maintenance
├── pricing
├── billing
├── document
├── claim
├── audit
├── notification
├── sustainability
├── identity
├── configuration
├── reporting
```

## Fase 0 — Documentazione

Stato: completata e riallineata.

File:

```text
docs/README.md
docs/architecture.md
docs/requirements.md
docs/domain-overview.md
docs/domain-model.md
docs/domain-class-catalog.md
docs/domain-rules.md
docs/glossary.md
docs/implementation-roadmap.md
docs/java-coding-guidelines.md
docs/shared-value-objects.md
docs/domain-reference.md
docs/architecture-decisions/
```

## Fase 1 — Domain puro

Stato: completata per MVP.

Include shared, cargo, route, shipment, fleet, driver, operation, pricing, billing, ecc.

## Fase 2 — Application layer

Stato: prossimo step consigliato.

Obiettivo:

- creare use case;
- creare porte repository;
- orchestrare il domain senza dettagli tecnici;
- non inserire Spring nel domain.

Use case iniziali consigliati:

```text
CreateTransportOrderUseCase
SubmitTransportOrderUseCase
AcceptTransportOrderUseCase
CreateShipmentFromOrderUseCase
PlanShipmentUseCase
CreateTransportMissionUseCase
DispatchMissionUseCase
CompleteMissionUseCase
IssueInvoiceUseCase
RegisterPaymentUseCase
GenerateReportUseCase
```

## Fase 3 — Repository port

Creare interfacce applicative:

```text
TransportOrderRepository
ShipmentRepository
DriverRepository
VehicleRepository
InvoiceRepository
AuditEventRepository
```

## Fase 4 — Infrastructure in-memory

Prima implementazione semplice:

```text
InMemoryTransportOrderRepository
InMemoryShipmentRepository
InMemoryDriverRepository
```

## Fase 5 — REST API

Introdurre controller e DTO.

Package futuro:

```text
it.gabriele.truckflow.web
```

## Fase 6 — Database

Introdurre PostgreSQL/JPA fuori dal domain.

## Fase 7 — Integrazioni esterne

Provider futuri:

- ViaMichelin;
- HERE;
- PTV;
- Google Maps;
- email;
- document storage.

Tutti fuori dal domain.

## Fase 8 — Frontend

Dashboard, gestione clienti, ordini, spedizioni, flotta, autisti, documenti, reporting.

## Regola finale

```text
Documentazione → Domain puro → Test → Application → Infrastructure → Web/API → Frontend
```
