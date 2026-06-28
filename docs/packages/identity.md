# Package `identity` — Identity

## Scopo

Gestisce account utenti, ruoli e permessi applicativi.

## Concetti principali

- `UserAccount`
- `UserRole`
- `UserPermission`
- `UserAccountStatus`
- `IdentityRules`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `IdentityRules` | final class | Classe statica di regole di business del package. |
| `UserAccount` | final class | Entity o value object del package. |
| `UserAccountStatus` | enum | Enum di classificazione/valori ammessi. |
| `UserPermission` | enum | Enum di classificazione/valori ammessi. |
| `UserRole` | enum | Enum di classificazione/valori ammessi. |

## Enum e valori ammessi

- `UserAccountStatus`: `INVITED`, `ACTIVE`, `LOCKED`, `DISABLED`, `DELETED`
- `UserPermission`: `VIEW_SHIPMENTS`, `MANAGE_SHIPMENTS`, `VIEW_OPERATIONS`, `MANAGE_OPERATIONS`, `MANAGE_FLEET`, `MANAGE_DRIVERS`, `MANAGE_BILLING`, `MANAGE_DOCUMENTS`, `MANAGE_CLAIMS`, `VIEW_REPORTS`, `VIEW_AUDIT`, `MANAGE_USERS`, `MANAGE_CONFIGURATION`
- `UserRole`: `ADMIN`, `DISPATCHER`, `PLANNER`, `ACCOUNTING`, `MAINTENANCE`, `DRIVER`, `CUSTOMER`, `VIEWER`

## Regole di business

- Account e permessi sono separati dalle entity operative come Driver o Customer.
- Password/hash/JWT reali rimangono fuori dal domain puro.

## Collegamenti con altri package

- audit, notification, future security infrastructure

## Test collegati

- `IdentityRulesTest.java`
- `UserAccountTest.java`

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
