# Package `availability` — Availability

## Scopo

Gestisce disponibilità o indisponibilità temporanea di risorse come veicoli, autisti o asset.

## Concetti principali

- `ResourceAvailability`
- `AvailabilityResourceType`
- `AvailabilityStatus`
- `AvailabilityRules`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `AvailabilityResourceType` | enum | Enum di classificazione/valori ammessi. |
| `AvailabilityRules` | final class | Classe statica di regole di business del package. |
| `AvailabilityStatus` | enum | Enum di classificazione/valori ammessi. |
| `ResourceAvailability` | final class | Entity o value object del package. |

## Enum e valori ammessi

- `AvailabilityResourceType`: `DRIVER`, `VEHICLE`, `VEHICLE_COMBINATION`, `TRAILER`, `FACILITY`
- `AvailabilityStatus`: `AVAILABLE`, `RESERVED`, `ASSIGNED`, `UNAVAILABLE`, `MAINTENANCE`, `ON_LEAVE`

## Regole di business

- Le finestre temporali non devono sovrapporsi in modo incoerente.
- Una risorsa bloccata o non disponibile non dovrebbe essere assegnata a missioni.

## Collegamenti con altri package

- fleet, driver, maintenance, operation

## Test collegati

- `AvailabilityRulesTest.java`
- `ResourceAvailabilityTest.java`

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
