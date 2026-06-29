# Package `claim` — Danni, sinistri e reclami

Gestisce danni, ispezioni, reclami cliente, severità e stato delle pratiche.

## Percorso

```text
src/main/java/it/gabriele/truckflow/domain/claim
```

## Classi

- `ClaimRules`
- `ClaimSeverity`
- `ClaimStatus`
- `ClaimType`
- `DamageInspection`
- `DamageInspectionItem`
- `TransportClaim`

## Test collegati

- `ClaimRulesTest`
- `TransportClaimTest`

## Ruolo nel sistema

Questo package contribuisce al domain model e deve restare indipendente da database, controller REST e framework esterni.
