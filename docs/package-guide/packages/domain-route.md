# `domain/route`

Tappe, pianificazione route e regole di percorso.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `RoutePlan` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_ROUTE_NUMBER_LENGTH, routeNumber, stops, estimatedDistance, notes | of, getRouteNumber, getStops, getEstimatedDistance, getNotes, getStopCount, getStartStop, getEndStop |
| `RoutePlanRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | hasCargoOperations, hasPickupAndDelivery, isWithinMaxDistance, startsAndEndsAtDifferentFacilities, usesOnlyActiveFacilities, isInternational, isOperationallyUsable |
| `RouteStop` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | sequenceNumber, type, facility, plannedTimeWindow, notes | of, getSequenceNumber, getType, getFacility, getPlannedTimeWindow, getNotes, isStart, isEnd |
| `RouteStopType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | cargoOperation | isCargoOperation |
