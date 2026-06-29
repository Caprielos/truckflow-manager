# `domain/billing`

Fatture cliente, pagamenti e stato incassi.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `BillingRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | canBeIssued, canBeMarkedPaid, canBeCancelled, hasReceivableAmount, isOverdue, isPaymentMatchingInvoice, canRegisterPayment, paymentsCoverInvoice |
| `Invoice` | class | Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini. | MAX_CODE_LENGTH, invoiceNumber, customerCode, shipmentNumber, priceBreakdown, issueDate, dueDate, status | draft, issued, issue, markPaid, cancel, getInvoiceNumber, getCustomerCode, getShipmentNumber |
| `InvoiceStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | terminal, canReceivePayment | isTerminal, canReceivePayment |
| `PaymentMethod` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | electronic | isElectronic |
| `PaymentRecord` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_CODE_LENGTH, paymentNumber, invoiceNumber, amount, method, receivedDate, notes | of, bankTransfer, getPaymentNumber, getInvoiceNumber, getAmount, getMethod, getReceivedDate, getNotes |
