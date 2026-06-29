# Domain `pricing` spiegato

Preventivi e breakdown prezzo cliente.

## Classi principali

### `CostEstimationSource`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `externalProvider`

Metodi pubblici principali:

- `isExternalProvider()`
- `isManual()`

### `PriceBreakdown`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_QUOTE_NUMBER_LENGTH`
- `quoteNumber`
- `lines`
- `notes`
- `reference`
- `total`

Metodi pubblici principali:

- `of()`
- `getQuoteNumber()`
- `getLines()`
- `getNotes()`
- `getLineCount()`
- `hasNotes()`
- `hasDiscounts()`
- `hasSurcharges()`
- `hasLineType()`
- `getChargeLines()`
- `getDiscountLines()`
- `calculateTotal()`

### `PricingLine`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_LINE_CODE_LENGTH`
- `MAX_DESCRIPTION_LENGTH`
- `lineCode`
- `type`
- `description`
- `amount`
- `notes`

Metodi pubblici principali:

- `of()`
- `baseFreight()`
- `surcharge()`
- `discount()`
- `fuelFromEstimate()`
- `tollsFromEstimate()`
- `vehicleWearFromEstimate()`
- `getLineCode()`
- `getType()`
- `getDescription()`
- `getAmount()`
- `getNotes()`

### `PricingLineType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `surcharge`
- `discount`

Metodi pubblici principali:

- `isSurcharge()`
- `isDiscount()`
- `increasesTotal()`
- `decreasesTotal()`

### `PricingRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `requiresAdrSurcharge()`
- `requiresTemperatureControlSurcharge()`
- `requiresInternationalSurcharge()`
- `hasDiscounts()`
- `hasSurcharges()`
- `hasBaseFreightLine()`
- `hasFuelSurchargeLine()`
- `hasTollChargeLine()`
- `hasVehicleWearChargeLine()`
- `hasAdrSurchargeLine()`
- `hasTemperatureControlSurchargeLine()`

### `RouteCostEstimate`

Tipo: `class`.

Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini.

Campi principali:

- `MAX_ESTIMATE_CODE_LENGTH`
- `estimateCode`
- `source`
- `estimatedDistance`
- `estimatedFuelCost`
- `estimatedTollCost`
- `estimatedVehicleWearCost`
- `notes`

Metodi pubblici principali:

- `of()`
- `getEstimateCode()`
- `getSource()`
- `getEstimatedDistance()`
- `getEstimatedFuelCost()`
- `getEstimatedTollCost()`
- `getEstimatedVehicleWearCost()`
- `getNotes()`
- `isFromExternalProvider()`
- `isManualEstimate()`
- `hasNotes()`
- `calculateEstimatedRouteCost()`
