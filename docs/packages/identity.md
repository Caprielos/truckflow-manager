# Package `identity` — Utenti, ruoli e permessi

Gestisce account applicativi separati da driver/customer, ruoli e permessi.

## Responsabilità

- UserAccount è account software, non autista né cliente.
- Ruoli e permessi servono al futuro web/API.

## Classi

- `IdentityRules` — classe di regole pure del package.
- `UserAccount` — modello/domain object del package.
- `UserAccountStatus` — enum con valori: `INVITED`, `ACTIVE`, `LOCKED`, `DISABLED`, `DELETED`.
- `UserPermission` — enum con valori: `VIEW_SHIPMENTS`, `MANAGE_SHIPMENTS`, `VIEW_OPERATIONS`, `MANAGE_OPERATIONS`, `MANAGE_FLEET`, `MANAGE_DRIVERS`, `MANAGE_BILLING`, `MANAGE_DOCUMENTS`, `MANAGE_CLAIMS`, `VIEW_REPORTS`, `VIEW_AUDIT`, `MANAGE_USERS`, `MANAGE_CONFIGURATION`.
- `UserRole` — enum con valori: `ADMIN`, `DISPATCHER`, `PLANNER`, `ACCOUNTING`, `MAINTENANCE`, `DRIVER`, `CUSTOMER`, `VIEWER`.

## Collegamenti

- UserAccount è account software, non autista né cliente.
- Ruoli e permessi servono al futuro web/API.
