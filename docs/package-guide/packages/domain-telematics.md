# `domain/telematics`

Snapshot GPS/CAN-bus, comportamento guida e dati telematici.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `DrivingBehaviorEvent` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | vehicleFleetNumber, type, occurredAt, coordinates, notes | of, getVehicleFleetNumber, getType, getOccurredAt, getCoordinates, getNotes, equals, hashCode |
| `DrivingBehaviorEventType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | — |
| `TelematicsRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | isFuelDropAnomaly, isSpeeding |
| `TelematicsSnapshot` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | vehicleFleetNumber, recordedAt, latitude, longitude, odometerKilometers, fuelLevelPercentage | of, getVehicleFleetNumber, getRecordedAt, getLatitude, getLongitude, getOdometerKilometers, getFuelLevelPercentage, equals |
