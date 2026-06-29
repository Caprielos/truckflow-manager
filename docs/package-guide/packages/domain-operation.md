# `domain/operation`

Missione operativa reale: autista, convoglio, rotta e stati missione.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `TransportMission` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_MISSION_NUMBER_LENGTH, missionNumber, shipment, driver, vehicleCombination, routePlan, status, notes | planned, dispatch, start, complete, cancel, getMissionNumber, getShipment, getDriver |
| `TransportMissionRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | isCompliant, canBeDispatched, canBeStarted, canBeCompleted, canBeCancelled, isCompleted, isTerminal, requiresSpecialHandling |
| `TransportMissionStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | terminal | isTerminal |
