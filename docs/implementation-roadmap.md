# Implementation Roadmap

## Stato attuale

Il domain layer è ampio e realistico. Ha già molti concetti utili per un gestionale trasporti reale.

Il prossimo salto non è aggiungere altri enum casuali. Il prossimo salto è costruire casi d'uso sopra il dominio.

## Fase 1 — Consolidamento domain

Già in gran parte completata:

- pulizia legacy shipment;
- modello realistico flotta;
- certificati driver;
- licenze azienda;
- moduli operativi tire/fuel/maintenance/telematics/loadsecurity;
- documentazione aggiornata.

Possibili piccoli miglioramenti futuri:

- `ShipmentRequirementType`;
- `ShipmentRequirementSummary`;
- `MissionReadinessReport`;
- maggiore integrazione documenti ↔ cargo ↔ missione.

## Fase 2 — Application Layer

Creare package futuri:

```text
application/order
application/shipment
application/operation
application/fleet
application/driver
application/compliance
```

Casi d'uso realistici:

- crea ordine;
- accetta ordine;
- genera shipment;
- pianifica missione;
- assegna driver;
- assegna convoglio;
- verifica mission readiness;
- registra pickup;
- registra delivery;
- genera fattura;
- apri reclamo;
- registra rifornimento;
- registra manutenzione;
- importa telematica.

## Fase 3 — Repository Ports

Definire interfacce, non implementazioni DB:

```text
TransportOrderRepository
ShipmentRepository
TransportMissionRepository
VehicleRepository
DriverRepository
CustomerRepository
DocumentRepository
```

## Fase 4 — Infrastructure Memory

Prima implementazione in memoria per testare flussi end-to-end senza database.

```text
infrastructure/memory
```

## Fase 5 — Web/API

Solo dopo domain + application + repository:

- Spring Boot;
- REST API;
- DTO;
- validation;
- security;
- database.

## Fase 6 — Integrazioni reali

- mappe e pedaggi;
- provider fuel card;
- GPS/telematica;
- document storage;
- notifiche email/SMS;
- import/export CSV/XLSX.
