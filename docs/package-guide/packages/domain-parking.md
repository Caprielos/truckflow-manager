# `domain/parking`

Posti parcheggio numerati e risorse parcheggiate, inclusi convogli già agganciati.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `ParkedResource` | class | Classe parcheggio: modella posto, risorsa parcheggiata o assegnazione. | MAX_ID_LENGTH, MAX_DISPLAY_NAME_LENGTH, type, resourceId, displayName, componentResourceIds, totalLengthMeters, readyForMission | van, rigidTruck, tractorUnit, trailer, semiTrailer, articulatedVehicle, truckAndTrailer, equipment |
| `ParkingAssignment` | class | Classe parcheggio: modella posto, risorsa parcheggiata o assegnazione. | MAX_CODE_LENGTH, assignmentCode, facilityCode, spotNumber, parkedResource, startedAt, endedAt, notes | active, closed, getAssignmentCode, getFacilityCode, getSpotNumber, getParkedResource, getStartedAt, getEndedAt |
| `ParkingResourceType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | isCombination, isTowedUnit, isPoweredSingleUnit |
| `ParkingRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | canPark, isReadyCombinationParked, isSpotFreeAt, isResourceAlreadyParkedAt |
| `ParkingSpot` | class | Classe parcheggio: modella posto, risorsa parcheggiata o assegnazione. | MAX_CODE_LENGTH, facilityCode, spotNumber, type, status, maxLengthMeters, maxWidthMeters, powerSupplyAvailable | of, available, occupied, getFacilityCode, getSpotNumber, getType, getStatus, getMaxLengthMeters |
| `ParkingSpotStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | canReceiveNewAssignment |
| `ParkingSpotType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | canHost |
