# Package `identity` — Utenti e permessi

## Scopo

Account, ruoli e autorizzazioni applicative.

## Classi ed enum

| Nome | Tipo | Ruolo sintetico |
|---|---|---|
| `IdentityRules` | Classe | Classe di regole di business del package. |
| `UserAccount` | Classe | Classe di dominio del package. |
| `UserAccountStatus` | Enum | Valori controllati usati dalle regole di dominio. |
| `UserPermission` | Enum | Valori controllati usati dalle regole di dominio. |
| `UserRole` | Enum | Valori controllati usati dalle regole di dominio. |

## Enum principali

### `UserAccountStatus`

Valori: `INVITED`, `ACTIVE`, `LOCKED`, `DISABLED`, `DELETED`.

### `UserPermission`

Valori: `VIEW_SHIPMENTS`, `MANAGE_SHIPMENTS`, `VIEW_OPERATIONS`, `MANAGE_OPERATIONS`, `MANAGE_FLEET`, `MANAGE_DRIVERS`, `MANAGE_BILLING`, `MANAGE_DOCUMENTS`, `MANAGE_CLAIMS`, `VIEW_REPORTS`, `VIEW_AUDIT`, `MANAGE_USERS`, `MANAGE_CONFIGURATION`.

### `UserRole`

Valori: `ADMIN`, `DISPATCHER`, `PLANNER`, `ACCOUNTING`, `MAINTENANCE`, `DRIVER`, `CUSTOMER`, `VIEWER`.



## Collegamenti con altri package

Questo package non vive isolato: le sue classi vengono usate dalle regole di business e dai casi d’uso futuri.

Per capire il flusso completo leggere anche:

- [`../domain-overview.md`](../domain-overview.md)
- [`../domain-rules.md`](../domain-rules.md)
- [`../domain-package-map.md`](../domain-package-map.md)

## Test collegati

I test si trovano sotto:

```text
src/test/java/it/gabriele/truckflow/domain/identity
```

Quando si modifica questo package, eseguire sempre:

```bash
mvn clean test
```
