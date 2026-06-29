# Package `domain.telematics`

Telematica: snapshot GPS/CAN e eventi comportamento guida.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| DrivingBehaviorEvent | class | Evento puntuale nella timeline operativa o audit. | of, getVehicleFleetNumber, getType, getOccurredAt, getCoordinates, getNotes, equals, hashCode |
| DrivingBehaviorEventType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | - |
| TelematicsRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.telematics. | isFuelDropAnomaly, isSpeeding |
| TelematicsSnapshot | class | Classe del package domain.telematics; rappresenta un concetto del modello TruckFlow. | of, getVehicleFleetNumber, getRecordedAt, getLatitude, getLongitude, getOdometerKilometers, getFuelLevelPercentage, equals, hashCode |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
