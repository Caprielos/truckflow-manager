# Domain `sustainability` spiegato

Emissioni e sostenibilità del trasporto.

## Classi principali

### `EmissionEstimate`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_CODE_LENGTH`
- `estimateNumber`
- `shipmentNumber`
- `routeNumber`
- `distance`
- `fuelType`
- `emissionStandard`
- `estimatedEnergyAmount`
- `estimatedCo2Kg`
- `rating`
- `notes`

Metodi pubblici principali:

- `of()`
- `getEstimateNumber()`
- `getShipmentNumber()`
- `getRouteNumber()`
- `getDistance()`
- `getFuelType()`
- `getEmissionStandard()`
- `getEstimatedEnergyAmount()`
- `getEstimatedCo2Kg()`
- `getRating()`
- `getNotes()`
- `isZeroTailpipeEmission()`

### `EmissionRating`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `level`

Metodi pubblici principali:

- `getLevel()`
- `isWorseThan()`

### `EmissionStandard`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `level`
- `lowEmissionStandard`

Metodi pubblici principali:

- `getLevel()`
- `isLowEmissionStandard()`
- `isAtLeast()`

### `FuelType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `combustionBased`
- `lowerEmissionAlternative`

Metodi pubblici principali:

- `isCombustionBased()`
- `isLowerEmissionAlternative()`
- `isZeroTailpipeEmission()`

### `SustainabilityRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `isLowEmissionTransport()`
- `isHighImpactTransport()`
- `requiresSustainabilityReview()`
- `isZeroTailpipeEmission()`
- `hasBetterEmissionStandard()`
- `calculateTotalCo2Kg()`
- `containsHighImpactEstimate()`
- `allEstimatesAreLowEmission()`
