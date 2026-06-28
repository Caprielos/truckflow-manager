# Package `maintenance` — Maintenance

## Scopo

Gestisce manutenzioni preventive, straordinarie, ticket autista e fermi macchina.

## Concetti principali

- `MaintenanceWorkOrder`
- `MaintenanceType`
- `MaintenanceStatus`
- `MaintenanceRules`
- `DriverDefectTicket`
- `VehicleDowntime`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `DriverDefectTicket` | final class | Segnalazione guasto/difetto fatta dall’autista. |
| `MaintenanceRules` | final class | Classe statica di regole di business del package. |
| `MaintenanceStatus` | enum | Enum di classificazione/valori ammessi. |
| `MaintenanceType` | enum | Enum di classificazione/valori ammessi. |
| `MaintenanceWorkOrder` | final class | Ordine di manutenzione. |
| `VehicleDowntime` | final class | Periodo di fermo macchina. |

## Enum e valori ammessi

- `MaintenanceStatus`: `OPEN`, `SCHEDULED`, `IN_PROGRESS`, `COMPLETED`, `CANCELLED`
- `MaintenanceType`: `ROUTINE_SERVICE`, `SAFETY_INSPECTION`, `TIRE_REPLACEMENT`, `REPAIR`, `REFRIGERATION_UNIT_SERVICE`, `ADR_TANK_INSPECTION`, `BREAKDOWN`, `ENGINE_SERVICE`, `AIR_DRYER_FILTER_REPLACEMENT`, `BRAKE_WEAR_CHECK`, `TIRE_ROTATION`, `DRIVER_DEFECT_TICKET`, `DOWNTIME`

## Regole di business

- Manutenzioni possono essere pianificate per km/data o generate da guasto.
- Un mezzo in fermo macchina o manutenzione non dovrebbe essere assegnabile.
- Costi e tempi permettono calcolo del costo di indisponibilità.

## Collegamenti con altri package

- fleet, availability, tire, claim, pricing

## Test collegati

- `MaintenanceRulesTest.java`
- `MaintenanceWorkOrderTest.java`

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
