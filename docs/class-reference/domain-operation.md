# Package `domain.operation`

Missione operativa reale: viaggio pianificato/eseguito con autista, convoglio e rotta.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| TransportMission | class | Classe del package domain.operation; rappresenta un concetto del modello TruckFlow. | planned, dispatch, start, complete, cancel, getMissionNumber, getShipment, getDriver, getVehicleCombination, getRoutePlan |
| TransportMissionRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.operation. | isCompliant, canBeDispatched, canBeStarted, canBeCompleted, canBeCancelled, isCompleted, isTerminal, requiresSpecialHandling |
| TransportMissionStatus | enum | Enum di stato del ciclo di vita. | isTerminal |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
