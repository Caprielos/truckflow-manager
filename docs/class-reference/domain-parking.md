# Package `domain.parking`

Posti parcheggio, risorse parcheggiate, convogli già agganciati e regole di occupazione.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| ParkedResource | class | Classe del package domain.parking; rappresenta un concetto del modello TruckFlow. | van, rigidTruck, tractorUnit, trailer, semiTrailer, articulatedVehicle, truckAndTrailer, equipment, getType, getResourceId |
| ParkingAssignment | class | Classe del package domain.parking; rappresenta un concetto del modello TruckFlow. | active, closed, getAssignmentCode, getFacilityCode, getSpotNumber, getParkedResource, getStartedAt, getEndedAt, getNotes, isActive |
| ParkingResourceType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | isCombination, isTowedUnit, isPoweredSingleUnit |
| ParkingRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.parking. | canPark, isReadyCombinationParked, isSpotFreeAt, isResourceAlreadyParkedAt |
| ParkingSpot | class | Classe del package domain.parking; rappresenta un concetto del modello TruckFlow. | of, available, occupied, getFacilityCode, getSpotNumber, getType, getStatus, getMaxLengthMeters, getMaxWidthMeters, isPowerSupplyAvailable |
| ParkingSpotStatus | enum | Enum di stato del ciclo di vita. | canReceiveNewAssignment |
| ParkingSpotType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | canHost |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
