# Package `audit` — Tracciamento audit

Registra azioni importanti, attori, severità e trail degli eventi. Serve per sapere chi ha fatto cosa e con quale impatto.

## Percorso

```text
src/main/java/it/gabriele/truckflow/domain/audit
```

## Classi

- `AuditActionType`
- `AuditActorType`
- `AuditEvent`
- `AuditRules`
- `AuditSeverity`
- `AuditTrail`

## Test collegati

- `AuditEventTest`
- `AuditRulesTest`
- `AuditTrailTest`

## Ruolo nel sistema

Questo package contribuisce al domain model e deve restare indipendente da database, controller REST e framework esterni.
