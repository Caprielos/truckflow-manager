# TruckFlow Manager — Architecture

## Scopo

Questo documento descrive l’architettura prevista del progetto.

L’obiettivo è mantenere il dominio indipendente e costruire l’applicazione in modo scalabile.

---

## Architettura a livelli

```text
Frontend Web
    ↓
REST API
    ↓
Application Layer
    ↓
Domain
    ↓
Infrastructure
```

---

## Domain Layer

Contiene le regole di business pure.

Non dipende da:

- Spring;
- database;
- REST;
- frontend;
- Google Maps;
- email;
- filesystem;
- autenticazione tecnica.

Contiene package come:

```text
driver
fleet
cargo
shipment
compliance
```

---

## Application Layer

Coordina i casi d’uso.

Esempi futuri:

```text
CreateShipmentUseCase
AssignDriverUseCase
AssignVehicleCombinationUseCase
CheckShipmentComplianceUseCase
CreateTransportMissionUseCase
CloseShipmentUseCase
```

L’application layer usa il dominio, ma non contiene regole profonde di business.

---

## Infrastructure Layer

Contiene dettagli tecnici.

Esempi futuri:

```text
PostgreSQLShipmentRepository
GoogleMapsRouteService
EmailNotificationSender
DocumentStorageAdapter
FuelPriceProvider
TollCostProvider
```

L’infrastructure dipende dal dominio, non il contrario.

---

## REST API

Espone il backend al frontend.

Esempi futuri:

```text
POST /api/shipments
POST /api/shipments/{id}/assign-driver
POST /api/shipments/{id}/assign-vehicle-combination
GET /api/fleet/vehicles
GET /api/drivers
```

Le API non devono contenere logica di dominio complessa.

---

## Frontend Web

Sarà la parte grafica usata da operatori e amministratori.

Viste future:

- dashboard;
- clienti;
- ordini;
- preventivi;
- spedizioni;
- missioni;
- flotta;
- autisti;
- documenti;
- alert;
- tracking.

---

## Regola delle dipendenze

Le dipendenze devono andare verso il dominio, mai dal dominio verso l’esterno.

```text
Frontend → API → Application → Domain
Infrastructure → Domain
```

Il dominio non deve importare classi di Spring, JPA, controller, repository o servizi esterni.

---

## Perché questa architettura

Questa architettura permette di:

- cambiare database senza riscrivere il dominio;
- cambiare frontend senza riscrivere il dominio;
- aggiungere Google Maps senza modificare `Route`;
- aggiungere regole compliance senza modificare tutto `Shipment`;
- testare il dominio con unit test semplici;
- costruire il progetto in modo professionale.
