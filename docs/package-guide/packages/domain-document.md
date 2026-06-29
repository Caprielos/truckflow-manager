# `domain/document`

Documenti di trasporto: bolla/DDT, CMR, POD, fascicoli documentali.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `DeliveryNote` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_CODE_LENGTH, documentNumber, shipmentNumber, senderCode, receiverCode, loadingLocationCode, unloadingLocationCode, issueDate | of, getDocumentNumber, getShipmentNumber, getSenderCode, getReceiverCode, getLoadingLocationCode, getUnloadingLocationCode, getIssueDate |
| `DeliveryNoteLine` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_CODE_LENGTH, MAX_DESCRIPTION_LENGTH, lineCode, description, packagesCount, grossWeightKilograms, volumeCubicMeters, palletCount | of, getLineCode, getDescription, getPackagesCount, getGrossWeightKilograms, getVolumeCubicMeters, getPalletCount, getNotes |
| `DocumentRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | canBeRequested, canBeReceived, canBeVerified, canBeRejected, canBeExpired, isExpiredOn, isValidForOperation, requiresExpirationDate |
| `DocumentStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | terminal, usableForOperation | isTerminal, isUsableForOperation |
| `ShipmentDocumentBundle` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_CODE_LENGTH, bundleCode, shipmentNumber, requiredTypes, documents, notes | of, getBundleCode, getShipmentNumber, getRequiredTypes, getDocuments, getNotes, presentTypes, missingRequiredTypes |
| `TransportDocument` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_CODE_LENGTH, documentNumber, type, referenceNumber, issueDate, expirationDate, status, notes | draft, received, verified, request, receive, verify, reject, expire |
| `TransportDocumentType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | shipmentRelated, invoiceRelated, requiredForAdr, proofOfDelivery, expirable | isShipmentRelated, isInvoiceRelated, isRequiredForAdr, isProofOfDelivery, isExpirable |
