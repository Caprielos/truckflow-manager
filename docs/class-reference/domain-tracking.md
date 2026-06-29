# Domain `tracking` spiegato

Timeline eventi tracking della spedizione/missione.

## Classi principali

### `TrackingEvent`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_CODE_LENGTH`
- `eventCode`
- `missionNumber`
- `shipmentNumber`
- `type`
- `occurredAt`
- `coordinates`
- `notes`

Metodi pubblici principali:

- `of()`
- `fromMission()`
- `positionRecorded()`
- `delayReported()`
- `incidentReported()`
- `getEventCode()`
- `getMissionNumber()`
- `getShipmentNumber()`
- `getType()`
- `getOccurredAt()`
- `getCoordinates()`
- `getNotes()`

### `TrackingEventType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `operationalMilestone`
- `exceptionEvent`
- `requiresCoordinates`

Metodi pubblici principali:

- `isOperationalMilestone()`
- `isExceptionEvent()`
- `requiresCoordinates()`

### `TrackingRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `canAddEvent()`
- `requiresOperationalReview()`
- `isPickupAndDeliveryCompleted()`
- `isMissionTrackingCompleted()`
- `hasExceptionEvents()`

### `TrackingTimeline`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `events`
- `first`
- `uniqueCodes`

Metodi pubblici principali:

- `of()`
- `getEvents()`
- `getEventCount()`
- `getMissionNumber()`
- `getShipmentNumber()`
- `getFirstEvent()`
- `getLatestEvent()`
- `containsEventCode()`
- `hasEventType()`
- `hasDelays()`
- `hasIncidents()`
- `hasPickupCompleted()`
