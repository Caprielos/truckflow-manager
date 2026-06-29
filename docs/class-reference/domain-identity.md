# Package `domain.identity`

Utenti, ruoli, permessi e stato account.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| IdentityRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.identity. | canLogin, canBeActivated, canBeLocked, canBeDisabled, canBeDeleted, canManageUsers, canViewAudit, canManageConfiguration, canViewEconomics, canManageEconomics |
| UserAccount | class | Classe del package domain.identity; rappresenta un concetto del modello TruckFlow. | active, invited, locked, disabled, activate, lock, disable, delete, getAccountId, getEmail |
| UserAccountStatus | enum | Enum di stato del ciclo di vita. | canLogin, isTerminal |
| UserPermission | enum | Enum: insieme chiuso di valori ammessi dal dominio. | isSensitive |
| UserRole | enum | Enum: insieme chiuso di valori ammessi dal dominio. | isBackOfficeRole, isDriverPortalRole, isCustomerPortalRole, isAdministrativeRole |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
