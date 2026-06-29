# Package `domain.billing`

Fatturazione e pagamenti cliente.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| BillingRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.billing. | canBeIssued, canBeMarkedPaid, canBeCancelled, hasReceivableAmount, isOverdue, isPaymentMatchingInvoice, canRegisterPayment, paymentsCoverInvoice |
| Invoice | class | Classe del package domain.billing; rappresenta un concetto del modello TruckFlow. | draft, issued, issue, markPaid, cancel, getInvoiceNumber, getCustomerCode, getShipmentNumber, getPriceBreakdown, getIssueDate |
| InvoiceStatus | enum | Enum di stato del ciclo di vita. | isTerminal, canReceivePayment |
| PaymentMethod | enum | Enum: insieme chiuso di valori ammessi dal dominio. | isElectronic |
| PaymentRecord | class | Classe del package domain.billing; rappresenta un concetto del modello TruckFlow. | of, bankTransfer, getPaymentNumber, getInvoiceNumber, getAmount, getMethod, getReceivedDate, getNotes, isElectronicPayment, hasNotes |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
