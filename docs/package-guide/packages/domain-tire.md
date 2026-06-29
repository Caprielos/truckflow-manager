# `domain/tire`

Gomme fisiche tracciabili, posizioni ruota, installazioni e stato pneumatico.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `Tire` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | tireCode, status, treadDepthMillimeters, installedAtKilometers, currentKilometers | of, getTireCode, getStatus, getTreadDepthMillimeters, getInstalledAtKilometers, getCurrentKilometers, calculateKilometersInUse, isBelowLegalMinimum |
| `TireInstallation` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | tire, vehicleFleetNumber, wheelPosition, installedAt, installedAtKilometers, removedAt, removedAtKilometers | active, remove, getTire, getVehicleFleetNumber, getWheelPosition, getInstalledAt, getInstalledAtKilometers, getRemovedAt |
| `TireRotationEvent` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | tireCode, vehicleFleetNumber, fromPosition, toPosition, occurredAt, odometerKilometers, notes | of, getTireCode, getVehicleFleetNumber, getFromPosition, getToPosition, getOccurredAt, getOdometerKilometers, getNotes |
| `TireRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | isLegallyUsable, shouldScheduleReplacement |
| `TireStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | — |
| `WheelPosition` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | axleNumber, side, slot | of, getAxleNumber, getSide, getSlot, formatLabel, equals, hashCode |
| `WheelSide` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | — |
| `WheelSlot` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | — |
