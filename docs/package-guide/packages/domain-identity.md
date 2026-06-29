# `domain/identity`

Account utente, ruoli e permessi applicativi.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `IdentityRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | canLogin, canBeActivated, canBeLocked, canBeDisabled, canBeDeleted, canManageUsers, canViewAudit, canManageConfiguration |
| `UserAccount` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_ACCOUNT_ID_LENGTH, MAX_EMAIL_LENGTH, MAX_DISPLAY_NAME_LENGTH, accountId, email, displayName, status, roles | active, invited, locked, disabled, activate, lock, disable, delete |
| `UserAccountStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | canLogin, terminal | canLogin, isTerminal |
| `UserPermission` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | sensitive | isSensitive |
| `UserRole` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | backOfficeRole, driverPortalRole, customerPortalRole, administrativeRole | isBackOfficeRole, isDriverPortalRole, isCustomerPortalRole, isAdministrativeRole |
