# Domain `dispatch` spiegato

Ufficio traffico: candidati di assegnazione, controlli readiness e piani di dispatch.

## Classi principali

### `DispatchAssignmentCandidate`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_CODE_LENGTH`
- `candidateCode`
- `missionNumber`
- `driverId`
- `vehicleFleetNumber`
- `trailerFleetNumber`
- `parkedResourceCode`
- `estimatedRevenue`
- `estimatedCost`
- `checks`
- `notes`

Metodi pubblici principali:

- `of()`
- `getCandidateCode()`
- `getMissionNumber()`
- `getDriverId()`
- `getVehicleFleetNumber()`
- `getTrailerFleetNumber()`
- `getParkedResourceCode()`
- `getEstimatedRevenue()`
- `getEstimatedCost()`
- `getChecks()`
- `getNotes()`
- `hasTrailer()`

### `DispatchCheckResult`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_MESSAGE_LENGTH`
- `type`
- `status`
- `message`

Metodi pubblici principali:

- `ready()`
- `warning()`
- `blocked()`
- `getType()`
- `getStatus()`
- `getMessage()`
- `blocksAssignment()`
- `requiresManualReview()`
- `equals()`
- `hashCode()`

### `DispatchCheckType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

### `DispatchPlan`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_CODE_LENGTH`
- `planCode`
- `planningDate`
- `candidates`
- `notes`

Metodi pubblici principali:

- `of()`
- `getPlanCode()`
- `getPlanningDate()`
- `getCandidates()`
- `getNotes()`
- `getAssignableCandidates()`
- `getBlockedCandidates()`
- `chooseBestAssignableByMargin()`

### `DispatchReadinessStatus`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Metodi pubblici principali:

- `blocksAssignment()`
- `requiresManualReview()`

### `DispatchRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `canAssign()`
- `shouldReviewBeforeAssigning()`
- `planHasAssignableCandidate()`
