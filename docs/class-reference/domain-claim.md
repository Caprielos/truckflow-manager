# Domain `claim` spiegato

Danni, reclami, incidenti e ispezioni danni.

## Classi principali

### `ClaimRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `canBeReviewed()`
- `canBeAccepted()`
- `canBeRejected()`
- `canBeSettled()`
- `canBeCancelled()`
- `isOpenForAction()`
- `isResolved()`
- `requiresUrgentReview()`
- `isAcceptedCompensationWithinRequestedAmount()`

### `ClaimSeverity`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `level`
- `urgent`

Metodi pubblici principali:

- `getLevel()`
- `isUrgent()`
- `isAtLeast()`

### `ClaimStatus`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `terminal`

Metodi pubblici principali:

- `isTerminal()`

### `ClaimType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `cargoRelated`
- `timeRelated`
- `documentRelated`
- `financialDispute`

Metodi pubblici principali:

- `isCargoRelated()`
- `isTimeRelated()`
- `isDocumentRelated()`
- `isFinancialDispute()`

### `DamageInspection`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `inspectionNumber`
- `vehicleFleetNumber`
- `driverCode`
- `performedAt`
- `items`
- `notes`

Metodi pubblici principali:

- `of()`
- `getInspectionNumber()`
- `getVehicleFleetNumber()`
- `getDriverCode()`
- `getPerformedAt()`
- `getItems()`
- `getNotes()`
- `hasNewDamage()`

### `DamageInspectionItem`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `area`
- `damaged`
- `notes`

Metodi pubblici principali:

- `of()`
- `getArea()`
- `isDamaged()`
- `getNotes()`
- `equals()`
- `hashCode()`

### `TransportClaim`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_CODE_LENGTH`
- `claimNumber`
- `shipmentNumber`
- `customerCode`
- `type`
- `severity`
- `status`
- `requestedCompensation`
- `acceptedCompensation`
- `openedDate`
- `closedDate`
- `notes`

Metodi pubblici principali:

- `open()`
- `startReview()`
- `accept()`
- `settle()`
- `reject()`
- `cancel()`
- `getClaimNumber()`
- `getShipmentNumber()`
- `getCustomerCode()`
- `getType()`
- `getSeverity()`
- `getStatus()`
