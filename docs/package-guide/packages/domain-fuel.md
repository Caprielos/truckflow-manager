# `domain/fuel`

Rifornimenti carburante, carta carburante e transazioni fuel.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `FuelCardProvider` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | — |
| `FuelConsumptionRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | calculateKilometersPerLiter, isConsumptionAnomaly |
| `FuelTransaction` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | vehicleFleetNumber, occurredAt, liters, pricePerLiter, odometerKilometers, cardProvider | of, getVehicleFleetNumber, getOccurredAt, getLiters, getPricePerLiter, getOdometerKilometers, getCardProvider, calculateKilometersPerLiter |
