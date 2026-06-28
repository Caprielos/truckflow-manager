# Package `tire` — Tire

## Scopo

Gestisce pneumatici singoli, posizioni ruota, montaggi e rotazioni.

## Concetti principali

- `Tire`
- `WheelPosition`
- `TireInstallation`
- `TireRotationEvent`
- `TireStatus`
- `WheelSide`
- `WheelSlot`
- `TireRules`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `Tire` | final class | Singola copertura/pneumatico tracciata. |
| `TireInstallation` | final class | Montaggio di uno pneumatico su una posizione ruota. |
| `TireRotationEvent` | final class | Spostamento storico di una gomma. |
| `TireRules` | final class | Regole su battistrada, rotazioni e stato pneumatici. |
| `TireStatus` | enum | Enum di classificazione/valori ammessi. |
| `WheelPosition` | final class | Entity o value object del package. |
| `WheelSide` | enum | Enum di classificazione/valori ammessi. |
| `WheelSlot` | enum | Enum di classificazione/valori ammessi. |

## Enum e valori ammessi

- `TireStatus`: `NEW`, `RETREADED`, `REGROOVED`, `IN_USE`, `STORED`, `DISPOSED`
- `WheelSide`: `LEFT`, `RIGHT`, `CENTER`
- `WheelSlot`: `SINGLE`, `INNER`, `OUTER`

## Regole di business

- Ogni copertura può essere nuova, ricostruita o riscolpita.
- Battistrada, km installazione e rotazioni devono essere tracciabili.
- La posizione ruota dipende da asse, lato e slot interno/esterno.

## Collegamenti con altri package

- fleet, maintenance, telematics, fuel

## Test collegati

- `TireManagementTest.java`

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
