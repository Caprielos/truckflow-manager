# Domain `availability` spiegato

Disponibilità di risorse: veicoli, driver, rimorchi, strutture o altre risorse operative.

## Classi principali

### `AvailabilityResourceType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

### `AvailabilityRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `isResourceAvailableForWindow()`
- `hasBlockingRecordForWindow()`
- `canAddAvailabilityRecord()`
- `findRecordsForResource()`

### `AvailabilityStatus`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `bookable`
- `blocking`

Metodi pubblici principali:

- `isBookable()`
- `isBlocking()`

### `ResourceAvailability`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_RESOURCE_CODE_LENGTH`
- `resourceType`
- `resourceCode`
- `dateRange`
- `timeWindow`
- `status`
- `notes`

Metodi pubblici principali:

- `of()`
- `available()`
- `reserved()`
- `assigned()`
- `unavailable()`
- `maintenance()`
- `onLeave()`
- `getResourceType()`
- `getResourceCode()`
- `getDateRange()`
- `getTimeWindow()`
- `getStatus()`
