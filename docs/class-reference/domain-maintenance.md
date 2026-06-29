# Package `domain.maintenance`

Manutenzione, ticket difetti autista, fermi veicolo e ordini di lavoro.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| DriverDefectTicket | class | Classe del package domain.maintenance; rappresenta un concetto del modello TruckFlow. | of, getTicketNumber, getVehicleFleetNumber, getDriverCode, getReportedAt, getDefectDescription, isVehicleBlocked, getNotes, equals, hashCode |
| MaintenanceRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.maintenance. | canBeScheduled, canBeStarted, canBeCompleted, canBeCancelled, blocksVehicleAvailability, shouldMakeVehicleUnavailable, requiresAdrSpecialist, requiresTireService, isTerminal |
| MaintenanceStatus | enum | Enum di stato del ciclo di vita. | isTerminal, blocksVehicleAvailability |
| MaintenanceType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | isPlannedMaintenance, isSafetyCritical, isTireRelated, isAdrRelated |
| MaintenanceWorkOrder | class | Classe del package domain.maintenance; rappresenta un concetto del modello TruckFlow. | open, scheduled, schedule, start, complete, cancel, getWorkOrderNumber, getVehicle, getType, getStatus |
| VehicleDowntime | class | Classe del package domain.maintenance; rappresenta un concetto del modello TruckFlow. | of, getVehicleFleetNumber, getStartedAt, getEndedAt, getPartsCost, getLaborCost, getNotes, isOpen, calculateTotalCost, equals |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
