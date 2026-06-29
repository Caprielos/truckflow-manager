# Package `domain.tire`

Pneumatici come beni tracciabili: installazione, rotazione, stato e posizione ruota.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| Tire | class | Classe del package domain.tire; rappresenta un concetto del modello TruckFlow. | of, getTireCode, getStatus, getTreadDepthMillimeters, getInstalledAtKilometers, getCurrentKilometers, calculateKilometersInUse, isBelowLegalMinimum, equals, hashCode |
| TireInstallation | class | Classe del package domain.tire; rappresenta un concetto del modello TruckFlow. | active, remove, getTire, getVehicleFleetNumber, getWheelPosition, getInstalledAt, getInstalledAtKilometers, getRemovedAt, getRemovedAtKilometers, isActive |
| TireRotationEvent | class | Evento puntuale nella timeline operativa o audit. | of, getTireCode, getVehicleFleetNumber, getFromPosition, getToPosition, getOccurredAt, getOdometerKilometers, getNotes, equals, hashCode |
| TireRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.tire. | isLegallyUsable, shouldScheduleReplacement |
| TireStatus | enum | Enum di stato del ciclo di vita. | - |
| WheelPosition | class | Classe del package domain.tire; rappresenta un concetto del modello TruckFlow. | of, getAxleNumber, getSide, getSlot, formatLabel, equals, hashCode |
| WheelSide | enum | Enum: insieme chiuso di valori ammessi dal dominio. | - |
| WheelSlot | enum | Enum: insieme chiuso di valori ammessi dal dominio. | - |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
