# Package `tracking` — Tracking spedizione/missione

Gestisce eventi tracking e timeline di avanzamento operativo.

## Percorso

```text
src/main/java/it/gabriele/truckflow/domain/tracking
```

## Classi

- `TrackingEvent`
- `TrackingEventType`
- `TrackingRules`
- `TrackingTimeline`

## Test collegati

- `TrackingEventTest`
- `TrackingRulesTest`
- `TrackingTimelineTest`

## Ruolo nel sistema

Questo package contribuisce al domain model e deve restare indipendente da database, controller REST e framework esterni.
