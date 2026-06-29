# Package `availability` — Disponibilità risorse

Gestisce disponibilità e indisponibilità di autisti, veicoli, rimorchi, strutture o altre risorse operative.

## Percorso

```text
src/main/java/it/gabriele/truckflow/domain/availability
```

## Classi

- `AvailabilityResourceType`
- `AvailabilityRules`
- `AvailabilityStatus`
- `ResourceAvailability`

## Test collegati

- `AvailabilityRulesTest`
- `ResourceAvailabilityTest`

## Ruolo nel sistema

Questo package contribuisce al domain model e deve restare indipendente da database, controller REST e framework esterni.
