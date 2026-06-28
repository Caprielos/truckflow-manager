# Package `operation` — Operation

## Scopo

Rappresenta la missione reale: il viaggio operativo eseguito con autista, mezzo/convoglio e percorso.

## Concetti principali

- `TransportMission`
- `TransportMissionStatus`
- `TransportMissionRules`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `TransportMission` | final class | Missione operativa reale. |
| `TransportMissionRules` | final class | Classe statica di regole di business del package. |
| `TransportMissionStatus` | enum | Enum di classificazione/valori ammessi. |

## Enum e valori ammessi

- `TransportMissionStatus`: `PLANNED`, `DISPATCHED`, `IN_PROGRESS`, `COMPLETED`, `CANCELLED`

## Regole di business

- Una missione non è una richiesta commerciale: è il lavoro reale da pianificare/eseguire.
- Le assegnazioni devono rispettare disponibilità, compatibilità e compliance.

## Collegamenti con altri package

- shipment, driver, fleet, route, document, tracking, pricing

## Test collegati

- `TransportMissionRulesTest.java`
- `TransportMissionTest.java`

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
