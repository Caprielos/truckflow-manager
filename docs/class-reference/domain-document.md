# Domain `document` spiegato

Documenti di trasporto: bolla/DDT, CMR, POD, fascicoli documentali.

## Classi principali

### `DeliveryNote`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_CODE_LENGTH`
- `documentNumber`
- `shipmentNumber`
- `senderCode`
- `receiverCode`
- `loadingLocationCode`
- `unloadingLocationCode`
- `issueDate`
- `lines`
- `requiredTemperatureRange`
- `notes`

Metodi pubblici principali:

- `of()`
- `getDocumentNumber()`
- `getShipmentNumber()`
- `getSenderCode()`
- `getReceiverCode()`
- `getLoadingLocationCode()`
- `getUnloadingLocationCode()`
- `getIssueDate()`
- `getLines()`
- `getRequiredTemperatureRange()`
- `getNotes()`
- `calculateTotalPackages()`

### `DeliveryNoteLine`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_CODE_LENGTH`
- `MAX_DESCRIPTION_LENGTH`
- `lineCode`
- `description`
- `packagesCount`
- `grossWeightKilograms`
- `volumeCubicMeters`
- `palletCount`
- `notes`

Metodi pubblici principali:

- `of()`
- `getLineCode()`
- `getDescription()`
- `getPackagesCount()`
- `getGrossWeightKilograms()`
- `getVolumeCubicMeters()`
- `getPalletCount()`
- `getNotes()`
- `equals()`
- `hashCode()`

### `DocumentRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `canBeRequested()`
- `canBeReceived()`
- `canBeVerified()`
- `canBeRejected()`
- `canBeExpired()`
- `isExpiredOn()`
- `isValidForOperation()`
- `requiresExpirationDate()`
- `containsAdrDocument()`
- `containsProofOfDelivery()`
- `allDocumentsValidForOperation()`

### `DocumentStatus`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `terminal`
- `usableForOperation`

Metodi pubblici principali:

- `isTerminal()`
- `isUsableForOperation()`

### `ShipmentDocumentBundle`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_CODE_LENGTH`
- `bundleCode`
- `shipmentNumber`
- `requiredTypes`
- `documents`
- `notes`

Metodi pubblici principali:

- `of()`
- `getBundleCode()`
- `getShipmentNumber()`
- `getRequiredTypes()`
- `getDocuments()`
- `getNotes()`
- `presentTypes()`
- `missingRequiredTypes()`
- `isComplete()`
- `allPresentDocumentsAreVerified()`
- `isReadyForOperation()`

### `TransportDocument`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_CODE_LENGTH`
- `documentNumber`
- `type`
- `referenceNumber`
- `issueDate`
- `expirationDate`
- `status`
- `notes`

Metodi pubblici principali:

- `draft()`
- `received()`
- `verified()`
- `request()`
- `receive()`
- `verify()`
- `reject()`
- `expire()`
- `getDocumentNumber()`
- `getType()`
- `getReferenceNumber()`
- `getIssueDate()`

### `TransportDocumentType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `shipmentRelated`
- `invoiceRelated`
- `requiredForAdr`
- `proofOfDelivery`
- `expirable`

Metodi pubblici principali:

- `isShipmentRelated()`
- `isInvoiceRelated()`
- `isRequiredForAdr()`
- `isProofOfDelivery()`
- `isExpirable()`
