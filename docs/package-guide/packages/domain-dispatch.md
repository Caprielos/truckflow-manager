# `domain/dispatch`

Ufficio traffico: candidati di assegnazione, controlli readiness e piani di dispatch.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `DispatchAssignmentCandidate` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_CODE_LENGTH, candidateCode, missionNumber, driverId, vehicleFleetNumber, trailerFleetNumber, parkedResourceCode, estimatedRevenue | of, getCandidateCode, getMissionNumber, getDriverId, getVehicleFleetNumber, getTrailerFleetNumber, getParkedResourceCode, getEstimatedRevenue |
| `DispatchCheckResult` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_MESSAGE_LENGTH, type, status, message | ready, warning, blocked, getType, getStatus, getMessage, blocksAssignment, requiresManualReview |
| `DispatchCheckType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | — |
| `DispatchPlan` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_CODE_LENGTH, planCode, planningDate, candidates, notes | of, getPlanCode, getPlanningDate, getCandidates, getNotes, getAssignableCandidates, getBlockedCandidates, chooseBestAssignableByMargin |
| `DispatchReadinessStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | blocksAssignment, requiresManualReview |
| `DispatchRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | canAssign, shouldReviewBeforeAssigning, planHasAssignableCandidate |
