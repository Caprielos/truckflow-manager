# Package `facility` — Facility

## Scopo

Rappresenta luoghi operativi come magazzini, hub, terminal, officine o punti di carico/scarico.

## Concetti principali

- `Facility`
- `FacilityType`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `Facility` | final class | Entity o value object del package. |
| `FacilityType` | enum | Enum di classificazione/valori ammessi. |

## Enum e valori ammessi

- `FacilityType`: `WAREHOUSE`, `DEPOT`, `CUSTOMER_SITE`, `SUPPLIER_SITE`, `CROSS_DOCK`, `TERMINAL`, `PORT`, `AIRPORT`, `MAINTENANCE_CENTER`

## Regole di business

- Una facility ha tipo, posizione e dati operativi di riferimento.

## Collegamenti con altri package

- route, order, shipment, operation

## Test collegati

- `FacilityTest.java`

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
