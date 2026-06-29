# Domain `route` spiegato

Tappe, pianificazione route e regole di percorso.

## Classi principali

### `RoutePlan`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_ROUTE_NUMBER_LENGTH`
- `routeNumber`
- `stops`
- `estimatedDistance`
- `notes`

Metodi pubblici principali:

- `of()`
- `getRouteNumber()`
- `getStops()`
- `getEstimatedDistance()`
- `getNotes()`
- `getStopCount()`
- `getStartStop()`
- `getEndStop()`
- `getCargoOperationStops()`
- `hasPickupStop()`
- `hasDeliveryStop()`
- `isInternational()`

### `RoutePlanRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `hasCargoOperations()`
- `hasPickupAndDelivery()`
- `isWithinMaxDistance()`
- `startsAndEndsAtDifferentFacilities()`
- `usesOnlyActiveFacilities()`
- `isInternational()`
- `isOperationallyUsable()`

### `RouteStop`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `sequenceNumber`
- `type`
- `facility`
- `plannedTimeWindow`
- `notes`

Metodi pubblici principali:

- `of()`
- `getSequenceNumber()`
- `getType()`
- `getFacility()`
- `getPlannedTimeWindow()`
- `getNotes()`
- `isStart()`
- `isEnd()`
- `isPickup()`
- `isDelivery()`
- `isCargoOperation()`
- `isAtSameFacility()`

### `RouteStopType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `cargoOperation`

Metodi pubblici principali:

- `isCargoOperation()`
