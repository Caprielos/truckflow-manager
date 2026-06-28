# Package `telematics` — Telematics

## Scopo

Gestisce dati da GPS/blackbox/CAN-bus e comportamento di guida.

## Concetti principali

- `TelematicsSnapshot`
- `DrivingBehaviorEvent`
- `DrivingBehaviorEventType`
- `TelematicsRules`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `DrivingBehaviorEvent` | final class | Entity o value object del package. |
| `DrivingBehaviorEventType` | enum | Enum di classificazione/valori ammessi. |
| `TelematicsRules` | final class | Regole su eventi di guida e coerenza telematica. |
| `TelematicsSnapshot` | final class | Snapshot da blackbox/CAN-bus/GPS. |

## Enum e valori ammessi

- `DrivingBehaviorEventType`: `HARSH_BRAKING`, `HARSH_ACCELERATION`, `SPEEDING`, `IDLING_TOO_LONG`, `LOW_FUEL_LEVEL`, `POSSIBLE_FUEL_THEFT`, `ENGINE_FAULT`

## Regole di business

- Snapshot deve avere timestamp e dati coerenti.
- Eventi come frenata brusca o eccesso velocità possono attivare alert.

## Collegamenti con altri package

- tracking, fuel, maintenance, driver, sustainability

## Test collegati

- `TelematicsSnapshotTest.java`

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
