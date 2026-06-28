# Package `billing` — Fatturazione e pagamenti

Gestisce fatture, stato fattura, pagamenti e regole economiche base.

## Responsabilità

- Invoice nasce da ordine/spedizione completata.
- PaymentRecord traccia incassi.

## Classi

- `BillingRules` — classe di regole pure del package.
- `Invoice` — modello/domain object del package.
- `InvoiceStatus` — enum con valori: `DRAFT`, `ISSUED`, `PAID`, `CANCELLED`.
- `PaymentMethod` — enum con valori: `BANK_TRANSFER`, `CARD`, `CASH`, `DIRECT_DEBIT`, `CREDIT_NOTE`, `OTHER`.
- `PaymentRecord` — modello/domain object del package.

## Collegamenti

- Invoice nasce da ordine/spedizione completata.
- PaymentRecord traccia incassi.
