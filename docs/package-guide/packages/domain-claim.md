# `domain/claim`

Danni, reclami, incidenti e ispezioni danni.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `ClaimRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | canBeReviewed, canBeAccepted, canBeRejected, canBeSettled, canBeCancelled, isOpenForAction, isResolved, requiresUrgentReview |
| `ClaimSeverity` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | level, urgent | getLevel, isUrgent, isAtLeast |
| `ClaimStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | terminal | isTerminal |
| `ClaimType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | cargoRelated, timeRelated, documentRelated, financialDispute | isCargoRelated, isTimeRelated, isDocumentRelated, isFinancialDispute |
| `DamageInspection` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | inspectionNumber, vehicleFleetNumber, driverCode, performedAt, items, notes | of, getInspectionNumber, getVehicleFleetNumber, getDriverCode, getPerformedAt, getItems, getNotes, hasNewDamage |
| `DamageInspectionItem` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | area, damaged, notes | of, getArea, isDamaged, getNotes, equals, hashCode |
| `TransportClaim` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_CODE_LENGTH, claimNumber, shipmentNumber, customerCode, type, severity, status, requestedCompensation | open, startReview, accept, settle, reject, cancel, getClaimNumber, getShipmentNumber |
