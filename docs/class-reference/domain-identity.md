# Domain `identity` spiegato

Account utente, ruoli e permessi applicativi.

## Classi principali

### `IdentityRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `canLogin()`
- `canBeActivated()`
- `canBeLocked()`
- `canBeDisabled()`
- `canBeDeleted()`
- `canManageUsers()`
- `canViewAudit()`
- `canManageConfiguration()`
- `canViewEconomics()`
- `canManageEconomics()`
- `canViewPayroll()`
- `canManagePayroll()`

### `UserAccount`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_ACCOUNT_ID_LENGTH`
- `MAX_EMAIL_LENGTH`
- `MAX_DISPLAY_NAME_LENGTH`
- `accountId`
- `email`
- `displayName`
- `status`
- `roles`
- `permissions`
- `notes`

Metodi pubblici principali:

- `active()`
- `invited()`
- `locked()`
- `disabled()`
- `activate()`
- `lock()`
- `disable()`
- `delete()`
- `getAccountId()`
- `getEmail()`
- `getDisplayName()`
- `getStatus()`

### `UserAccountStatus`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `canLogin`
- `terminal`

Metodi pubblici principali:

- `canLogin()`
- `isTerminal()`

### `UserPermission`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `sensitive`

Metodi pubblici principali:

- `isSensitive()`

### `UserRole`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `backOfficeRole`
- `driverPortalRole`
- `customerPortalRole`
- `administrativeRole`

Metodi pubblici principali:

- `isBackOfficeRole()`
- `isDriverPortalRole()`
- `isCustomerPortalRole()`
- `isAdministrativeRole()`
