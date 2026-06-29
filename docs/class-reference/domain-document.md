# Package `domain.document`

Documenti di trasporto, DDT/bolla, fascicolo documentale e regole documentali.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| DeliveryNote | class | Classe del package domain.document; rappresenta un concetto del modello TruckFlow. | of, getDocumentNumber, getShipmentNumber, getSenderCode, getReceiverCode, getLoadingLocationCode, getUnloadingLocationCode, getIssueDate, getLines, getRequiredTemperatureRange |
| DeliveryNoteLine | class | Riga di dettaglio: rappresenta una voce numerabile/economica/documentale. | of, getLineCode, getDescription, getPackagesCount, getGrossWeightKilograms, getVolumeCubicMeters, getPalletCount, getNotes, equals, hashCode |
| DocumentRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.document. | canBeRequested, canBeReceived, canBeVerified, canBeRejected, canBeExpired, isExpiredOn, isValidForOperation, requiresExpirationDate, containsAdrDocument, containsProofOfDelivery |
| DocumentStatus | enum | Enum di stato del ciclo di vita. | isTerminal, isUsableForOperation |
| ShipmentDocumentBundle | class | Fascicolo/raccolta di elementi collegati alla stessa spedizione o processo. | of, getBundleCode, getShipmentNumber, getRequiredTypes, getDocuments, getNotes, presentTypes, missingRequiredTypes, isComplete, allPresentDocumentsAreVerified |
| TransportDocument | class | Classe del package domain.document; rappresenta un concetto del modello TruckFlow. | draft, received, verified, request, receive, verify, reject, expire, getDocumentNumber, getType |
| TransportDocumentType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | isShipmentRelated, isInvoiceRelated, isRequiredForAdr, isProofOfDelivery, isExpirable |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
