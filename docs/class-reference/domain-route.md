# Package `domain.route`

Piano di viaggio con fermate, distanze e finestre temporali.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| RoutePlan | class | Piano composto da più elementi e usato per organizzare un processo operativo. | of, getRouteNumber, getStops, getEstimatedDistance, getNotes, getStopCount, getStartStop, getEndStop, getCargoOperationStops, hasPickupStop |
| RoutePlanRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.route. | hasCargoOperations, hasPickupAndDelivery, isWithinMaxDistance, startsAndEndsAtDifferentFacilities, usesOnlyActiveFacilities, isInternational, isOperationallyUsable |
| RouteStop | class | Classe del package domain.route; rappresenta un concetto del modello TruckFlow. | of, getSequenceNumber, getType, getFacility, getPlannedTimeWindow, getNotes, isStart, isEnd, isPickup, isDelivery |
| RouteStopType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | isCargoOperation |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
