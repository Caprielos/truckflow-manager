# Package `telematics` — Telematica e comportamento guida

Gestisce snapshot GPS/CAN-bus e eventi comportamento guida come frenate, accelerazioni, consumo e odometro.

## Percorso

```text
src/main/java/it/gabriele/truckflow/domain/telematics
```

## Classi

- `DrivingBehaviorEvent`
- `DrivingBehaviorEventType`
- `TelematicsRules`
- `TelematicsSnapshot`

## Test collegati

- `TelematicsSnapshotTest`

## Ruolo nel sistema

Questo package contribuisce al domain model e deve restare indipendente da database, controller REST e framework esterni.
