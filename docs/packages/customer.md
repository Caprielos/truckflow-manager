# Package `customer` — Customer

## Scopo

Gestisce cliente, account e contatti commerciali/operativi.

## Concetti principali

- `Customer`
- `CustomerAccount`
- `CustomerContact`
- `CustomerContactRole`
- `CustomerStatus`
- `CustomerType`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `Customer` | final class | Entity o value object del package. |
| `CustomerAccount` | final class | Entity o value object del package. |
| `CustomerContact` | final class | Entity o value object del package. |
| `CustomerContactRole` | enum | Enum di classificazione/valori ammessi. |
| `CustomerStatus` | enum | Enum di classificazione/valori ammessi. |
| `CustomerType` | enum | Enum di classificazione/valori ammessi. |

## Enum e valori ammessi

- `CustomerContactRole`: `LOGISTICS`, `ADMINISTRATION`, `BILLING`, `OPERATIONS`, `SALES`, `MANAGEMENT`, `OTHER`
- `CustomerStatus`: `ACTIVE`, `INACTIVE`, `SUSPENDED`
- `CustomerType`: `INDIVIDUAL`, `COMPANY`, `PUBLIC_AUTHORITY`, `INTERNAL`

## Regole di business

- Un cliente ha stato, tipo, contatti e account.
- I contatti hanno ruoli come amministrativo, operativo o fatturazione.

## Collegamenti con altri package

- order, billing, notification

## Test collegati

- `CustomerAccountTest.java`
- `CustomerContactTest.java`
- `CustomerTest.java`

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
