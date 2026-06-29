# Package `domain.sustainability`

Stime emissioni, standard ambientali e rating.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| EmissionEstimate | class | Stima calcolata prima del dato finale effettivo. | of, getEstimateNumber, getShipmentNumber, getRouteNumber, getDistance, getFuelType, getEmissionStandard, getEstimatedEnergyAmount, getEstimatedCo2Kg, getRating |
| EmissionRating | enum | Enum: insieme chiuso di valori ammessi dal dominio. | getLevel, isWorseThan |
| EmissionStandard | enum | Enum: insieme chiuso di valori ammessi dal dominio. | getLevel, isLowEmissionStandard, isAtLeast |
| FuelType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | isCombustionBased, isLowerEmissionAlternative, isZeroTailpipeEmission |
| SustainabilityRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.sustainability. | isLowEmissionTransport, isHighImpactTransport, requiresSustainabilityReview, isZeroTailpipeEmission, hasBetterEmissionStandard, calculateTotalCo2Kg, containsHighImpactEstimate, allEstimatesAreLowEmission |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
