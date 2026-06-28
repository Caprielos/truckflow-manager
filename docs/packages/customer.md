# Package `customer` — Clienti e contatti

## Scopo

Anagrafica clienti, account e contatti operativi/commerciali.

## Classi ed enum

| Nome | Tipo | Ruolo sintetico |
|---|---|---|
| `Customer` | Classe | Classe di dominio del package. |
| `CustomerAccount` | Classe | Classe di dominio del package. |
| `CustomerContact` | Classe | Classe di dominio del package. |
| `CustomerContactRole` | Enum | Valori controllati usati dalle regole di dominio. |
| `CustomerStatus` | Enum | Valori controllati usati dalle regole di dominio. |
| `CustomerType` | Enum | Valori controllati usati dalle regole di dominio. |

## Enum principali

### `CustomerContactRole`

Valori: `LOGISTICS`, `ADMINISTRATION`, `BILLING`, `OPERATIONS`, `SALES`, `MANAGEMENT`, `OTHER`.

### `CustomerStatus`

Valori: `ACTIVE`, `INACTIVE`, `SUSPENDED`.

### `CustomerType`

Valori: `INDIVIDUAL`, `COMPANY`, `PUBLIC_AUTHORITY`, `INTERNAL`.



## Collegamenti con altri package

Questo package non vive isolato: le sue classi vengono usate dalle regole di business e dai casi d’uso futuri.

Per capire il flusso completo leggere anche:

- [`../domain-overview.md`](../domain-overview.md)
- [`../domain-rules.md`](../domain-rules.md)
- [`../domain-package-map.md`](../domain-package-map.md)

## Test collegati

I test si trovano sotto:

```text
src/test/java/it/gabriele/truckflow/domain/customer
```

Quando si modifica questo package, eseguire sempre:

```bash
mvn clean test
```
