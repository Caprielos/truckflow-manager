# Package `parking` — Parcheggi e posti numerati

Gestisce posti, risorse parcheggiate, furgoni, rimorchi, trattori, convogli agganciati e readiness.

## Percorso

```text
src/main/java/it/gabriele/truckflow/domain/parking
```

## Classi

- `ParkedResource`
- `ParkingAssignment`
- `ParkingResourceType`
- `ParkingRules`
- `ParkingSpot`
- `ParkingSpotStatus`
- `ParkingSpotType`

## Test collegati

- `ParkingAssignmentTest`

## Ruolo nel sistema

Questo package contribuisce al domain model e deve restare indipendente da database, controller REST e framework esterni.
