# Package `billing` — Fatturazione e pagamenti

## Scopo

Gestisce fatture, stati, importi e pagamenti collegati a spedizioni e prezzi.

## Classi ed enum

| Nome | Tipo | Ruolo sintetico |
|---|---|---|
| `BillingRules` | Classe | Classe di regole di business del package. |
| `Invoice` | Classe | Classe di dominio del package. |
| `InvoiceStatus` | Enum | Valori controllati usati dalle regole di dominio. |
| `PaymentMethod` | Enum | Valori controllati usati dalle regole di dominio. |
| `PaymentRecord` | Classe | Classe di dominio del package. |

## Enum principali

### `InvoiceStatus`

Valori: `DRAFT`, `ISSUED`, `PAID`, `CANCELLED`.

### `PaymentMethod`

Valori: `BANK_TRANSFER`, `CARD`, `CASH`, `DIRECT_DEBIT`, `CREDIT_NOTE`, `OTHER`.



## Collegamenti con altri package

Questo package non vive isolato: le sue classi vengono usate dalle regole di business e dai casi d’uso futuri.

Per capire il flusso completo leggere anche:

- [`../domain-overview.md`](../domain-overview.md)
- [`../domain-rules.md`](../domain-rules.md)
- [`../domain-package-map.md`](../domain-package-map.md)

## Test collegati

I test si trovano sotto:

```text
src/test/java/it/gabriele/truckflow/domain/billing
```

Quando si modifica questo package, eseguire sempre:

```bash
mvn clean test
```
