# Package `tire` — Pneumatici

Gestisce gomma fisica, installazioni, rotazioni, stato, posizioni ruota e regole usura/sicurezza.

## Percorso

```text
src/main/java/it/gabriele/truckflow/domain/tire
```

## Classi

- `Tire`
- `TireInstallation`
- `TireRotationEvent`
- `TireRules`
- `TireStatus`
- `WheelPosition`
- `WheelSide`
- `WheelSlot`

## Test collegati

- `TireManagementTest`

## Ruolo nel sistema

Questo package contribuisce al domain model e deve restare indipendente da database, controller REST e framework esterni.
