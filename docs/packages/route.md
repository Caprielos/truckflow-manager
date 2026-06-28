# Package `route` — Route

## Scopo

Gestisce piano percorso, fermate, pickup, delivery e stop intermedi.

## Concetti principali

- `RoutePlan`
- `RouteStop`
- `RouteStopType`
- `RoutePlanRules`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `RoutePlan` | final class | Entity o value object del package. |
| `RoutePlanRules` | final class | Classe statica di regole di business del package. |
| `RouteStop` | final class | Entity o value object del package. |
| `RouteStopType` | enum | Enum di classificazione/valori ammessi. |

## Enum e valori ammessi

- `RouteStopType`: `START`, `PICKUP`, `DELIVERY`, `REST_BREAK`, `FUEL_STOP`, `END`

## Regole di business

- Fermate hanno ordine logico.
- Pickup e delivery devono essere coerenti.
- Il percorso può essere base per costi, tempi e tracking.

## Collegamenti con altri package

- location, operation, pricing, drivetime

## Test collegati

- `RoutePlanRulesTest.java`
- `RoutePlanTest.java`
- `RouteStopTest.java`

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
