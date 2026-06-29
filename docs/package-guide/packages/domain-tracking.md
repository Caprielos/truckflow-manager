# `domain/tracking`

Timeline eventi tracking della spedizione/missione.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `TrackingEvent` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_CODE_LENGTH, eventCode, missionNumber, shipmentNumber, type, occurredAt, coordinates, notes | of, fromMission, positionRecorded, delayReported, incidentReported, getEventCode, getMissionNumber, getShipmentNumber |
| `TrackingEventType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | operationalMilestone, exceptionEvent, requiresCoordinates | isOperationalMilestone, isExceptionEvent, requiresCoordinates |
| `TrackingRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | canAddEvent, requiresOperationalReview, isPickupAndDeliveryCompleted, isMissionTrackingCompleted, hasExceptionEvents |
| `TrackingTimeline` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | events, first, uniqueCodes | of, getEvents, getEventCount, getMissionNumber, getShipmentNumber, getFirstEvent, getLatestEvent, containsEventCode |
