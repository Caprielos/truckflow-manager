# Package `billing` — Fatturazione cliente

Rappresenta fatture emesse, stato fattura, pagamenti e regole base di incasso.

## Percorso

```text
src/main/java/it/gabriele/truckflow/domain/billing
```

## Classi

- `BillingRules`
- `Invoice`
- `InvoiceStatus`
- `PaymentMethod`
- `PaymentRecord`

## Test collegati

- `BillingRulesTest`
- `InvoiceTest`
- `PaymentRecordTest`

## Ruolo nel sistema

Questo package contribuisce al domain model e deve restare indipendente da database, controller REST e framework esterni.
