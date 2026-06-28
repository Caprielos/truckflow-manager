# Package `audit` — Audit trail

Registra eventi di audit per modifiche, login, permessi, verifiche documentali e azioni sensibili.

## Responsabilità

- AuditTrail registra azioni sensibili.
- Separato dal tracking: audit riguarda il sistema, tracking il viaggio.

## Classi

- `AuditActionType` — enum con valori: `CREATED`, `UPDATED`, `STATUS_CHANGED`, `ASSIGNED`, `CANCELLED`, `DELETED`, `DOCUMENT_VERIFIED`, `PAYMENT_REGISTERED`, `CLAIM_SETTLED`, `EXTERNAL_ESTIMATE_IMPORTED`, `LOGIN`, `LOGIN_FAILED`, `PERMISSION_DENIED`.
- `AuditActorType` — enum con valori: `USER`, `SYSTEM`, `INTEGRATION`.
- `AuditEvent` — modello/domain object del package.
- `AuditRules` — classe di regole pure del package.
- `AuditSeverity` — enum con valori: `INFO`, `WARNING`, `ERROR`, `CRITICAL`.
- `AuditTrail` — modello/domain object del package.

## Collegamenti

- AuditTrail registra azioni sensibili.
- Separato dal tracking: audit riguarda il sistema, tracking il viaggio.
