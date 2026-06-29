# Package `domain.dispatch`

Ufficio traffico: candidati, controlli di readiness e piano dispatch.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| DispatchAssignmentCandidate | class | Classe del package domain.dispatch; rappresenta un concetto del modello TruckFlow. | of, getCandidateCode, getMissionNumber, getDriverId, getVehicleFleetNumber, getTrailerFleetNumber, getParkedResourceCode, getEstimatedRevenue, getEstimatedCost, getChecks |
| DispatchCheckResult | class | Risultato di una valutazione o calcolo. | ready, warning, blocked, getType, getStatus, getMessage, blocksAssignment, requiresManualReview, equals, hashCode |
| DispatchCheckType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | - |
| DispatchPlan | class | Piano composto da più elementi e usato per organizzare un processo operativo. | of, getPlanCode, getPlanningDate, getCandidates, getNotes, getAssignableCandidates, getBlockedCandidates, chooseBestAssignableByMargin |
| DispatchReadinessStatus | enum | Enum di stato del ciclo di vita. | blocksAssignment, requiresManualReview |
| DispatchRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.dispatch. | canAssign, shouldReviewBeforeAssigning, planHasAssignableCandidate |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
