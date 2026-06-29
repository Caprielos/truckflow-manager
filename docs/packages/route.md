# Package `route` — Percorsi e soste

Gestisce route plan, stop, carico/scarico, sequenza e regole di coerenza della rotta.

## Percorso

```text
src/main/java/it/gabriele/truckflow/domain/route
```

## Classi

- `RoutePlan`
- `RoutePlanRules`
- `RouteStop`
- `RouteStopType`

## Test collegati

- `RoutePlanRulesTest`
- `RoutePlanTest`
- `RouteStopTest`

## Ruolo nel sistema

Questo package contribuisce al domain model e deve restare indipendente da database, controller REST e framework esterni.
