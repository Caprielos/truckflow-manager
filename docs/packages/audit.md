# Package `audit` — Audit

## Scopo

Registra gli eventi importanti del sistema: chi ha fatto cosa, quando, con che severità e su quale risorsa.

## Concetti principali

- `AuditEvent`
- `AuditTrail`
- `AuditActionType`
- `AuditActorType`
- `AuditSeverity`
- `AuditRules`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `AuditActionType` | enum | Enum di classificazione/valori ammessi. |
| `AuditActorType` | enum | Enum di classificazione/valori ammessi. |
| `AuditEvent` | final class | Entity o value object del package. |
| `AuditRules` | final class | Classe statica di regole di business del package. |
| `AuditSeverity` | enum | Enum di classificazione/valori ammessi. |
| `AuditTrail` | final class | Entity o value object del package. |

## Enum e valori ammessi

- `AuditActionType`: `CREATED`, `UPDATED`, `STATUS_CHANGED`, `ASSIGNED`, `CANCELLED`, `DELETED`, `DOCUMENT_VERIFIED`, `PAYMENT_REGISTERED`, `CLAIM_SETTLED`, `EXTERNAL_ESTIMATE_IMPORTED`, `LOGIN`, `LOGIN_FAILED`, `PERMISSION_DENIED`
- `AuditActorType`: `USER`, `SYSTEM`, `INTEGRATION`
- `AuditSeverity`: `INFO`, `WARNING`, `ERROR`, `CRITICAL`

## Regole di business

- Un audit event deve avere actor, action, timestamp e risorsa coerenti.
- AuditTrail conserva una sequenza immutabile di eventi.
- La severità permette di distinguere eventi informativi, warning e critici.

## Collegamenti con altri package

- identity per l’utente/attore
- operation/order/shipment per tracciare azioni operative
- compliance per eventi critici o blocchi

## Test collegati

- `AuditEventTest.java`
- `AuditRulesTest.java`
- `AuditTrailTest.java`

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
