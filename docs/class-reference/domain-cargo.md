# Domain `cargo` spiegato

Merce trasportata: categoria, peso, volume, temperatura, ADR e regole operative cargo.

## Classi principali

### `AdrClass`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `code`

Metodi pubblici principali:

- `getCode()`
- `isExplosives()`
- `isGas()`
- `isFlammableLiquid()`
- `isRadioactive()`

### `CargoCategory`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `requiresTemperatureControl`
- `palletized`
- `requiresAdrData`
- `liquid`
- `bulk`
- `waste`
- `sanitarySensitive`
- `oversized`
- `highValueOrVehicle`

Metodi pubblici principali:

- `requiresTemperatureControl()`
- `isPalletized()`
- `requiresAdrData()`
- `isLiquid()`
- `isBulk()`
- `isWaste()`
- `requiresSanitaryOrVeterinaryDocuments()`
- `requiresFirDocument()`
- `requiresEerCode()`
- `isOversized()`
- `isHighValueOrVehicle()`

### `CargoItem`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_DESCRIPTION_LENGTH`
- `description`
- `category`
- `weight`
- `dimension`
- `requiredTemperatureRange`
- `dangerousGoodsProfile`
- `notes`

Metodi pubblici principali:

- `of()`
- `temperatureControlled()`
- `dangerousGoods()`
- `temperatureControlledDangerousGoods()`
- `getDescription()`
- `getCategory()`
- `getWeight()`
- `getDimension()`
- `getRequiredTemperatureRange()`
- `getDangerousGoodsProfile()`
- `getNotes()`
- `calculateVolume()`

### `CargoLoad`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `items`

Metodi pubblici principali:

- `of()`
- `getItems()`
- `getItemCount()`
- `calculateTotalWeight()`
- `calculateTotalVolume()`
- `requiresTemperatureControl()`
- `containsDangerousGoods()`
- `hasDangerousGoodsProfile()`
- `requiresAdrTransport()`
- `requiresAdrTankTransport()`
- `containsAdrClass()`
- `hasCategory()`

### `CargoLoadRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `isWithinMaxWeight()`
- `isWithinMaxVolume()`
- `fitsInsideCargoSpace()`
- `requiresTemperatureControlledTransport()`
- `containsHazardousMaterial()`
- `containsDangerousGoods()`
- `requiresAdrTransport()`
- `requiresAdrTankTransport()`
- `containsExplosives()`
- `containsRadioactiveMaterial()`
- `containsFragileCargo()`
- `containsOversizedCargo()`

### `CargoOperationalRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `requiredDocumentsFor()`
- `requiredVehicleCertificatesFor()`
- `requiresEerCode()`
- `requiresUnNumber()`

### `DangerousGoodsProfile`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_PROPER_SHIPPING_NAME_LENGTH`
- `MAX_CLASSIFICATION_CODE_LENGTH`
- `MAX_TUNNEL_RESTRICTION_CODE_LENGTH`
- `unNumber`
- `properShippingName`
- `adrClass`
- `classificationCode`
- `packingGroup`
- `hazardLabels`
- `tunnelRestrictionCode`
- `transportCategory`
- `requiresTankTransport`

Metodi pubblici principali:

- `of()`
- `getUnNumber()`
- `getProperShippingName()`
- `getAdrClass()`
- `getClassificationCode()`
- `getPackingGroup()`
- `getHazardLabels()`
- `getTunnelRestrictionCode()`
- `getTransportCategory()`
- `requiresTankTransport()`
- `hasPackingGroup()`
- `isGas()`

### `HazardLabel`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `code`

Metodi pubblici principali:

- `getCode()`
- `isGasLabel()`
- `isTankRelevantLabel()`

### `PackingGroup`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `description`

Metodi pubblici principali:

- `getDescription()`
- `isHighDanger()`
- `isMediumDanger()`
- `isLowDanger()`
