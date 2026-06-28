# Package `tracking` — Tracking

## Scopo

Gestisce eventi di tracking logistico e timeline della spedizione/missione.

## Concetti principali

- `TrackingEvent`
- `TrackingTimeline`
- `TrackingEventType`
- `TrackingRules`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `TrackingEvent` | final class | Entity o value object del package. |
| `TrackingEventType` | enum | Enum di classificazione/valori ammessi. |
| `TrackingRules` | final class | Classe statica di regole di business del package. |
| `TrackingTimeline` | final class | Entity o value object del package. |

## Enum e valori ammessi

- `TrackingEventType`: `POSITION_RECORDED`, `DEPARTED`, `ARRIVED`, `PICKUP_COMPLETED`, `DELIVERY_COMPLETED`, `DELAY_REPORTED`, `INCIDENT_REPORTED`, `MISSION_COMPLETED`, `CAN_BUS_SNAPSHOT`, `HARSH_BRAKING`, `SPEEDING`, `FUEL_LEVEL_RECORDED`

## Regole di business

- Eventi devono avere timestamp e ordine coerente.
- La timeline ricostruisce avanzamento e anomalie.

## Collegamenti con altri package

- shipment, operation, telematics, notification

## Test collegati

- `TrackingEventTest.java`
- `TrackingRulesTest.java`
- `TrackingTimelineTest.java`

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
