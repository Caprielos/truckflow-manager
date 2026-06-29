# Domain `facility` spiegato

Strutture aziendali: deposito, sede, magazzino, piazzale, proprietà/affitto e spese.

## Classi principali

### `Facility`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_CODE_LENGTH`
- `code`
- `type`
- `location`
- `operatingHours`
- `notes`
- `active`

Metodi pubblici principali:

- `active()`
- `inactive()`
- `getCode()`
- `getType()`
- `getLocation()`
- `getOperatingHours()`
- `getNotes()`
- `isActive()`
- `isOpenAt()`
- `isInCountry()`
- `hasCoordinates()`
- `hasNotes()`

### `FacilityCostFrequency`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Metodi pubblici principali:

- `isRecurring()`

### `FacilityCostLine`

Tipo: `class`.

Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini.

Campi principali:

- `MAX_CODE_LENGTH`
- `MAX_DESCRIPTION_LENGTH`
- `code`
- `type`
- `description`
- `amount`
- `frequency`
- `coveragePeriod`
- `notes`

Metodi pubblici principali:

- `of()`
- `monthly()`
- `yearly()`
- `oneTime()`
- `getCode()`
- `getType()`
- `getDescription()`
- `getAmount()`
- `getFrequency()`
- `getCoveragePeriod()`
- `getNotes()`
- `isRecurring()`

### `FacilityCostType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

### `FacilityFinancialProfile`

Tipo: `class`.

Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini.

Campi principali:

- `MAX_FACILITY_CODE_LENGTH`
- `facilityCode`
- `ownershipType`
- `purchasePrice`
- `monthlyRent`
- `depositAmount`
- `costLines`
- `notes`
- `referenceCurrency`

Metodi pubblici principali:

- `owned()`
- `rented()`
- `leased()`
- `thirdPartyYard()`
- `getFacilityCode()`
- `getOwnershipType()`
- `getPurchasePrice()`
- `getMonthlyRent()`
- `getDepositAmount()`
- `getCostLines()`
- `getNotes()`
- `isOwned()`

### `FacilityOwnershipType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Metodi pubblici principali:

- `isOwnedAsset()`
- `requiresRecurringOccupancyPayment()`

### `FacilityType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.
