# Package `domain.fuel`

Rifornimenti, fuel card e regole consumo carburante.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| FuelCardProvider | enum | Enum: insieme chiuso di valori ammessi dal dominio. | - |
| FuelConsumptionRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.fuel. | calculateKilometersPerLiter, isConsumptionAnomaly |
| FuelTransaction | class | Classe del package domain.fuel; rappresenta un concetto del modello TruckFlow. | of, getVehicleFleetNumber, getOccurredAt, getLiters, getPricePerLiter, getOdometerKilometers, getCardProvider, calculateKilometersPerLiter, equals, hashCode |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
