# Package `domain.tracking`

Tracking operativo e timeline eventi.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| TrackingEvent | class | Evento puntuale nella timeline operativa o audit. | of, fromMission, positionRecorded, delayReported, incidentReported, getEventCode, getMissionNumber, getShipmentNumber, getType, getOccurredAt |
| TrackingEventType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | isOperationalMilestone, isExceptionEvent, requiresCoordinates |
| TrackingRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.tracking. | canAddEvent, requiresOperationalReview, isPickupAndDeliveryCompleted, isMissionTrackingCompleted, hasExceptionEvents |
| TrackingTimeline | class | Sequenza ordinata di eventi. | of, getEvents, getEventCount, getMissionNumber, getShipmentNumber, getFirstEvent, getLatestEvent, containsEventCode, hasEventType, hasDelays |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
