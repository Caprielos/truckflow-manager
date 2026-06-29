# `domain/sustainability`

Emissioni e sostenibilità del trasporto.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `EmissionEstimate` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_CODE_LENGTH, estimateNumber, shipmentNumber, routeNumber, distance, fuelType, emissionStandard, estimatedEnergyAmount | of, getEstimateNumber, getShipmentNumber, getRouteNumber, getDistance, getFuelType, getEmissionStandard, getEstimatedEnergyAmount |
| `EmissionRating` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | level | getLevel, isWorseThan |
| `EmissionStandard` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | level, lowEmissionStandard | getLevel, isLowEmissionStandard, isAtLeast |
| `FuelType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | combustionBased, lowerEmissionAlternative | isCombustionBased, isLowerEmissionAlternative, isZeroTailpipeEmission |
| `SustainabilityRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | isLowEmissionTransport, isHighImpactTransport, requiresSustainabilityReview, isZeroTailpipeEmission, hasBetterEmissionStandard, calculateTotalCo2Kg, containsHighImpactEstimate, allEstimatesAreLowEmission |
