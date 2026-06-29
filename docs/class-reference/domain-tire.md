# Domain `tire` spiegato

Gomme fisiche tracciabili, posizioni ruota, installazioni e stato pneumatico.

## Classi principali

### `Tire`

Tipo: `class`.

Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.

Campi principali:

- `tireCode`
- `status`
- `treadDepthMillimeters`
- `installedAtKilometers`
- `currentKilometers`

Metodi pubblici principali:

- `of()`
- `getTireCode()`
- `getStatus()`
- `getTreadDepthMillimeters()`
- `getInstalledAtKilometers()`
- `getCurrentKilometers()`
- `calculateKilometersInUse()`
- `isBelowLegalMinimum()`
- `equals()`
- `hashCode()`

### `TireInstallation`

Tipo: `class`.

Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.

Campi principali:

- `tire`
- `vehicleFleetNumber`
- `wheelPosition`
- `installedAt`
- `installedAtKilometers`
- `removedAt`
- `removedAtKilometers`

Metodi pubblici principali:

- `active()`
- `remove()`
- `getTire()`
- `getVehicleFleetNumber()`
- `getWheelPosition()`
- `getInstalledAt()`
- `getInstalledAtKilometers()`
- `getRemovedAt()`
- `getRemovedAtKilometers()`
- `isActive()`
- `calculateKilometersMounted()`
- `equals()`

### `TireRotationEvent`

Tipo: `class`.

Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.

Campi principali:

- `tireCode`
- `vehicleFleetNumber`
- `fromPosition`
- `toPosition`
- `occurredAt`
- `odometerKilometers`
- `notes`

Metodi pubblici principali:

- `of()`
- `getTireCode()`
- `getVehicleFleetNumber()`
- `getFromPosition()`
- `getToPosition()`
- `getOccurredAt()`
- `getOdometerKilometers()`
- `getNotes()`
- `equals()`
- `hashCode()`

### `TireRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `isLegallyUsable()`
- `shouldScheduleReplacement()`

### `TireStatus`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

### `WheelPosition`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `axleNumber`
- `side`
- `slot`

Metodi pubblici principali:

- `of()`
- `getAxleNumber()`
- `getSide()`
- `getSlot()`
- `formatLabel()`
- `equals()`
- `hashCode()`

### `WheelSide`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

### `WheelSlot`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
