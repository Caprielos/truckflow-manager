# Package `domain.pricing`

Prezzo/preventivo verso il cliente e breakdown commerciale.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| CostEstimationSource | enum | Enum: insieme chiuso di valori ammessi dal dominio. | isExternalProvider, isManual |
| PriceBreakdown | class | Classe del package domain.pricing; rappresenta un concetto del modello TruckFlow. | of, getQuoteNumber, getLines, getNotes, getLineCount, hasNotes, hasDiscounts, hasSurcharges, hasLineType, getChargeLines |
| PricingLine | class | Riga di dettaglio: rappresenta una voce numerabile/economica/documentale. | of, baseFreight, surcharge, discount, fuelFromEstimate, tollsFromEstimate, vehicleWearFromEstimate, getLineCode, getType, getDescription |
| PricingLineType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | isSurcharge, isDiscount, increasesTotal, decreasesTotal |
| PricingRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.pricing. | requiresAdrSurcharge, requiresTemperatureControlSurcharge, requiresInternationalSurcharge, hasDiscounts, hasSurcharges, hasBaseFreightLine, hasFuelSurchargeLine, hasTollChargeLine, hasVehicleWearChargeLine, hasAdrSurchargeLine |
| RouteCostEstimate | class | Stima calcolata prima del dato finale effettivo. | of, getEstimateCode, getSource, getEstimatedDistance, getEstimatedFuelCost, getEstimatedTollCost, getEstimatedVehicleWearCost, getNotes, isFromExternalProvider, isManualEstimate |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
