# Package `audit` — Audit e tracciabilità

## Scopo

Registra eventi importanti del dominio, chi li ha generati, quando sono avvenuti e se richiedono revisione.

## Classi ed enum

| Nome | Tipo | Ruolo sintetico |
|---|---|---|
| `AuditActionType` | Enum | Valori controllati usati dalle regole di dominio. |
| `AuditActorType` | Enum | Valori controllati usati dalle regole di dominio. |
| `AuditEvent` | Classe | Classe di dominio del package. |
| `AuditRules` | Classe | Classe di regole di business del package. |
| `AuditSeverity` | Enum | Valori controllati usati dalle regole di dominio. |
| `AuditTrail` | Classe | Classe di dominio del package. |

## Enum principali

### `AuditActionType`

Valori: `CREATED`, `UPDATED`, `STATUS_CHANGED`, `ASSIGNED`, `CANCELLED`, `DELETED`, `DOCUMENT_VERIFIED`, `PAYMENT_REGISTERED`, `CLAIM_SETTLED`, `EXTERNAL_ESTIMATE_IMPORTED`, `LOGIN`, `LOGIN_FAILED`, `PERMISSION_DENIED`.

### `AuditActorType`

Valori: `USER`, `SYSTEM`, `INTEGRATION`.

### `AuditSeverity`

Valori: `INFO`, `WARNING`, `ERROR`, `CRITICAL`.



## Collegamenti con altri package

Questo package non vive isolato: le sue classi vengono usate dalle regole di business e dai casi d’uso futuri.

Per capire il flusso completo leggere anche:

- [`../domain-overview.md`](../domain-overview.md)
- [`../domain-rules.md`](../domain-rules.md)
- [`../domain-package-map.md`](../domain-package-map.md)

## Test collegati

I test si trovano sotto:

```text
src/test/java/it/gabriele/truckflow/domain/audit
```

Quando si modifica questo package, eseguire sempre:

```bash
mvn clean test
```
