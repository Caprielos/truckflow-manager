# `domain/maintenance`

Manutenzione veicoli, ordini lavoro, scadenze e ticket difetti driver.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `DriverDefectTicket` | class | Classe legata all’autista, alle sue abilitazioni o al suo costo operativo. | ticketNumber, vehicleFleetNumber, driverCode, reportedAt, defectDescription, vehicleBlocked, notes | of, getTicketNumber, getVehicleFleetNumber, getDriverCode, getReportedAt, getDefectDescription, isVehicleBlocked, getNotes |
| `MaintenanceRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | canBeScheduled, canBeStarted, canBeCompleted, canBeCancelled, blocksVehicleAvailability, shouldMakeVehicleUnavailable, requiresAdrSpecialist, requiresTireService |
| `MaintenanceStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | terminal, blockingVehicleAvailability | isTerminal, blocksVehicleAvailability |
| `MaintenanceType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | plannedMaintenance, safetyCritical, tireRelated, adrRelated | isPlannedMaintenance, isSafetyCritical, isTireRelated, isAdrRelated |
| `MaintenanceWorkOrder` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_WORK_ORDER_NUMBER_LENGTH, workOrderNumber, vehicle, type, status, plannedDateRange, notes | open, scheduled, schedule, start, complete, cancel, getWorkOrderNumber, getVehicle |
| `VehicleDowntime` | class | Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset. | vehicleFleetNumber, startedAt, endedAt, partsCost, laborCost, notes | of, getVehicleFleetNumber, getStartedAt, getEndedAt, getPartsCost, getLaborCost, getNotes, isOpen |
