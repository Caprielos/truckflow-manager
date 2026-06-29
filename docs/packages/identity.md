# Package `identity` — Utenti, ruoli e permessi

Gestisce account utente, ruoli e permessi per distinguere accesso operativo, amministrativo ed economico.

## Percorso

```text
src/main/java/it/gabriele/truckflow/domain/identity
```

## Classi

- `IdentityRules`
- `UserAccount`
- `UserAccountStatus`
- `UserPermission`
- `UserRole`

## Test collegati

- `ExpandedIdentityPermissionsTest`
- `IdentityRulesTest`
- `UserAccountTest`

## Ruolo nel sistema

Questo package contribuisce al domain model e deve restare indipendente da database, controller REST e framework esterni.
