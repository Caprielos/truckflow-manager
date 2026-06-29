# Domain `telematics` spiegato

Snapshot GPS/CAN-bus, comportamento guida e dati telematici.

## Classi principali

### `DrivingBehaviorEvent`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `vehicleFleetNumber`
- `type`
- `occurredAt`
- `coordinates`
- `notes`

Metodi pubblici principali:

- `of()`
- `getVehicleFleetNumber()`
- `getType()`
- `getOccurredAt()`
- `getCoordinates()`
- `getNotes()`
- `equals()`
- `hashCode()`

### `DrivingBehaviorEventType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

### `TelematicsRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `isFuelDropAnomaly()`
- `isSpeeding()`

### `TelematicsSnapshot`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `vehicleFleetNumber`
- `recordedAt`
- `latitude`
- `longitude`
- `odometerKilometers`
- `fuelLevelPercentage`

Metodi pubblici principali:

- `of()`
- `getVehicleFleetNumber()`
- `getRecordedAt()`
- `getLatitude()`
- `getLongitude()`
- `getOdometerKilometers()`
- `getFuelLevelPercentage()`
- `equals()`
- `hashCode()`
