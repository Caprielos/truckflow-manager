# Package `dispatch` — Ufficio traffico / pianificazione

Valuta candidati di assegnazione autista/mezzo/convoglio, readiness e scelta del candidato migliore.

## Percorso

```text
src/main/java/it/gabriele/truckflow/domain/dispatch
```

## Classi

- `DispatchAssignmentCandidate`
- `DispatchCheckResult`
- `DispatchCheckType`
- `DispatchPlan`
- `DispatchReadinessStatus`
- `DispatchRules`

## Test collegati

- `DispatchPlanningTest`

## Ruolo nel sistema

Questo package contribuisce al domain model e deve restare indipendente da database, controller REST e framework esterni.
