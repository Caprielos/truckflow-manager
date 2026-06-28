# Package `billing` — Billing

## Scopo

Gestisce fatture, pagamenti e regole amministrative economiche.

## Concetti principali

- `Invoice`
- `PaymentRecord`
- `InvoiceStatus`
- `PaymentMethod`
- `BillingRules`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `BillingRules` | final class | Classe statica di regole di business del package. |
| `Invoice` | final class | Entity o value object del package. |
| `InvoiceStatus` | enum | Enum di classificazione/valori ammessi. |
| `PaymentMethod` | enum | Enum di classificazione/valori ammessi. |
| `PaymentRecord` | final class | Entity o value object del package. |

## Enum e valori ammessi

- `InvoiceStatus`: `DRAFT`, `ISSUED`, `PAID`, `CANCELLED`
- `PaymentMethod`: `BANK_TRANSFER`, `CARD`, `CASH`, `DIRECT_DEBIT`, `CREDIT_NOTE`, `OTHER`

## Regole di business

- Una fattura segue stati coerenti da bozza/emessa/pagata/annullata.
- I pagamenti devono essere collegati a importi e date validi.

## Collegamenti con altri package

- pricing per importi preventivati
- customer per il cliente
- order/shipment per fonte commerciale

## Test collegati

- `BillingRulesTest.java`
- `InvoiceTest.java`
- `PaymentRecordTest.java`

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
