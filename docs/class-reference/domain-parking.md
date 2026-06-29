# Domain `parking` spiegato

Posti parcheggio numerati e risorse parcheggiate, inclusi convogli già agganciati.

## Classi principali

### `ParkedResource`

Tipo: `class`.

Classe parcheggio: modella posto, risorsa parcheggiata o assegnazione.

Campi principali:

- `MAX_ID_LENGTH`
- `MAX_DISPLAY_NAME_LENGTH`
- `type`
- `resourceId`
- `displayName`
- `componentResourceIds`
- `totalLengthMeters`
- `readyForMission`

Metodi pubblici principali:

- `van()`
- `rigidTruck()`
- `tractorUnit()`
- `trailer()`
- `semiTrailer()`
- `articulatedVehicle()`
- `truckAndTrailer()`
- `equipment()`
- `getType()`
- `getResourceId()`
- `getDisplayName()`
- `getComponentResourceIds()`

### `ParkingAssignment`

Tipo: `class`.

Classe parcheggio: modella posto, risorsa parcheggiata o assegnazione.

Campi principali:

- `MAX_CODE_LENGTH`
- `assignmentCode`
- `facilityCode`
- `spotNumber`
- `parkedResource`
- `startedAt`
- `endedAt`
- `notes`

Metodi pubblici principali:

- `active()`
- `closed()`
- `getAssignmentCode()`
- `getFacilityCode()`
- `getSpotNumber()`
- `getParkedResource()`
- `getStartedAt()`
- `getEndedAt()`
- `getNotes()`
- `isActive()`
- `isActiveAt()`
- `parksCombination()`

### `ParkingResourceType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Metodi pubblici principali:

- `isCombination()`
- `isTowedUnit()`
- `isPoweredSingleUnit()`

### `ParkingRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `canPark()`
- `isReadyCombinationParked()`
- `isSpotFreeAt()`
- `isResourceAlreadyParkedAt()`

### `ParkingSpot`

Tipo: `class`.

Classe parcheggio: modella posto, risorsa parcheggiata o assegnazione.

Campi principali:

- `MAX_CODE_LENGTH`
- `facilityCode`
- `spotNumber`
- `type`
- `status`
- `maxLengthMeters`
- `maxWidthMeters`
- `powerSupplyAvailable`
- `notes`

Metodi pubblici principali:

- `of()`
- `available()`
- `occupied()`
- `getFacilityCode()`
- `getSpotNumber()`
- `getType()`
- `getStatus()`
- `getMaxLengthMeters()`
- `getMaxWidthMeters()`
- `isPowerSupplyAvailable()`
- `getNotes()`
- `isAvailable()`

### `ParkingSpotStatus`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Metodi pubblici principali:

- `canReceiveNewAssignment()`

### `ParkingSpotType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Metodi pubblici principali:

- `canHost()`
