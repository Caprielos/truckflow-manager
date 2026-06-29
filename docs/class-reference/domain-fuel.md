# Domain `fuel` spiegato

Rifornimenti carburante, carta carburante e transazioni fuel.

## Classi principali

### `FuelCardProvider`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

### `FuelConsumptionRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `calculateKilometersPerLiter()`
- `isConsumptionAnomaly()`

### `FuelTransaction`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `vehicleFleetNumber`
- `occurredAt`
- `liters`
- `pricePerLiter`
- `odometerKilometers`
- `cardProvider`

Metodi pubblici principali:

- `of()`
- `getVehicleFleetNumber()`
- `getOccurredAt()`
- `getLiters()`
- `getPricePerLiter()`
- `getOdometerKilometers()`
- `getCardProvider()`
- `calculateKilometersPerLiter()`
- `equals()`
- `hashCode()`
