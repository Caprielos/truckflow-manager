# Package `domain.claim`

Danni, sinistri, reclami e ispezioni.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| ClaimRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.claim. | canBeReviewed, canBeAccepted, canBeRejected, canBeSettled, canBeCancelled, isOpenForAction, isResolved, requiresUrgentReview, isAcceptedCompensationWithinRequestedAmount |
| ClaimSeverity | enum | Enum: insieme chiuso di valori ammessi dal dominio. | getLevel, isUrgent, isAtLeast |
| ClaimStatus | enum | Enum di stato del ciclo di vita. | isTerminal |
| ClaimType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | isCargoRelated, isTimeRelated, isDocumentRelated, isFinancialDispute |
| DamageInspection | class | Classe del package domain.claim; rappresenta un concetto del modello TruckFlow. | of, getInspectionNumber, getVehicleFleetNumber, getDriverCode, getPerformedAt, getItems, getNotes, hasNewDamage |
| DamageInspectionItem | class | Classe del package domain.claim; rappresenta un concetto del modello TruckFlow. | of, getArea, isDamaged, getNotes, equals, hashCode |
| TransportClaim | class | Classe del package domain.claim; rappresenta un concetto del modello TruckFlow. | open, startReview, accept, settle, reject, cancel, getClaimNumber, getShipmentNumber, getCustomerCode, getType |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
