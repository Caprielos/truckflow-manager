# Domain `billing` spiegato

Fatture cliente, pagamenti e stato incassi.

## Classi principali

### `BillingRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `canBeIssued()`
- `canBeMarkedPaid()`
- `canBeCancelled()`
- `hasReceivableAmount()`
- `isOverdue()`
- `isPaymentMatchingInvoice()`
- `canRegisterPayment()`
- `paymentsCoverInvoice()`

### `Invoice`

Tipo: `class`.

Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini.

Campi principali:

- `MAX_CODE_LENGTH`
- `invoiceNumber`
- `customerCode`
- `shipmentNumber`
- `priceBreakdown`
- `issueDate`
- `dueDate`
- `status`
- `notes`

Metodi pubblici principali:

- `draft()`
- `issued()`
- `issue()`
- `markPaid()`
- `cancel()`
- `getInvoiceNumber()`
- `getCustomerCode()`
- `getShipmentNumber()`
- `getPriceBreakdown()`
- `getIssueDate()`
- `getDueDate()`
- `getStatus()`

### `InvoiceStatus`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `terminal`
- `canReceivePayment`

Metodi pubblici principali:

- `isTerminal()`
- `canReceivePayment()`

### `PaymentMethod`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `electronic`

Metodi pubblici principali:

- `isElectronic()`

### `PaymentRecord`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_CODE_LENGTH`
- `paymentNumber`
- `invoiceNumber`
- `amount`
- `method`
- `receivedDate`
- `notes`

Metodi pubblici principali:

- `of()`
- `bankTransfer()`
- `getPaymentNumber()`
- `getInvoiceNumber()`
- `getAmount()`
- `getMethod()`
- `getReceivedDate()`
- `getNotes()`
- `isElectronicPayment()`
- `hasNotes()`
- `isForInvoice()`
- `isForInvoiceNumber()`
