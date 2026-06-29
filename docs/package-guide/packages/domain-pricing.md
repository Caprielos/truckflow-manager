# `domain/pricing`

Preventivi e breakdown prezzo cliente.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `CostEstimationSource` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | externalProvider | isExternalProvider, isManual |
| `PriceBreakdown` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_QUOTE_NUMBER_LENGTH, quoteNumber, lines, notes, reference, total | of, getQuoteNumber, getLines, getNotes, getLineCount, hasNotes, hasDiscounts, hasSurcharges |
| `PricingLine` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_LINE_CODE_LENGTH, MAX_DESCRIPTION_LENGTH, lineCode, type, description, amount, notes | of, baseFreight, surcharge, discount, fuelFromEstimate, tollsFromEstimate, vehicleWearFromEstimate, getLineCode |
| `PricingLineType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | surcharge, discount | isSurcharge, isDiscount, increasesTotal, decreasesTotal |
| `PricingRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | requiresAdrSurcharge, requiresTemperatureControlSurcharge, requiresInternationalSurcharge, hasDiscounts, hasSurcharges, hasBaseFreightLine, hasFuelSurchargeLine, hasTollChargeLine |
| `RouteCostEstimate` | class | Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini. | MAX_ESTIMATE_CODE_LENGTH, estimateCode, source, estimatedDistance, estimatedFuelCost, estimatedTollCost, estimatedVehicleWearCost, notes | of, getEstimateCode, getSource, getEstimatedDistance, getEstimatedFuelCost, getEstimatedTollCost, getEstimatedVehicleWearCost, getNotes |
