# Package `shipment` — Shipment

## Scopo

Rappresenta la spedizione logistica derivata da un ordine accettato.

## Concetti principali

- `Shipment`
- `ShipmentStatus`
- `ShipmentRules`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `Shipment` | final class | Spedizione accettata derivata da ordine. |
| `ShipmentRules` | final class | Classe statica di regole di business del package. |
| `ShipmentStatus` | enum | Enum di classificazione/valori ammessi. |

## Enum e valori ammessi

- `ShipmentStatus`: `CREATED`, `PLANNED`, `DISPATCHED`, `IN_TRANSIT`, `DELIVERED`, `CANCELLED`

## Regole di business

- Una spedizione nasce da un ordine accettato.
- Segue stati operativi propri separati dalla missione.

## Collegamenti con altri package

- order, cargo, operation, tracking, document

## Test collegati

- `ShipmentRulesTest.java`
- `ShipmentTest.java`

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
