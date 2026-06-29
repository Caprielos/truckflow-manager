# Domain `maintenance` spiegato

Manutenzione veicoli, ordini lavoro, scadenze e ticket difetti driver.

## Classi principali

### `DriverDefectTicket`

Tipo: `class`.

Classe legata all’autista, alle sue abilitazioni o al suo costo operativo.

Campi principali:

- `ticketNumber`
- `vehicleFleetNumber`
- `driverCode`
- `reportedAt`
- `defectDescription`
- `vehicleBlocked`
- `notes`

Metodi pubblici principali:

- `of()`
- `getTicketNumber()`
- `getVehicleFleetNumber()`
- `getDriverCode()`
- `getReportedAt()`
- `getDefectDescription()`
- `isVehicleBlocked()`
- `getNotes()`
- `equals()`
- `hashCode()`

### `MaintenanceRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `canBeScheduled()`
- `canBeStarted()`
- `canBeCompleted()`
- `canBeCancelled()`
- `blocksVehicleAvailability()`
- `shouldMakeVehicleUnavailable()`
- `requiresAdrSpecialist()`
- `requiresTireService()`
- `isTerminal()`

### `MaintenanceStatus`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `terminal`
- `blockingVehicleAvailability`

Metodi pubblici principali:

- `isTerminal()`
- `blocksVehicleAvailability()`

### `MaintenanceType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `plannedMaintenance`
- `safetyCritical`
- `tireRelated`
- `adrRelated`

Metodi pubblici principali:

- `isPlannedMaintenance()`
- `isSafetyCritical()`
- `isTireRelated()`
- `isAdrRelated()`

### `MaintenanceWorkOrder`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_WORK_ORDER_NUMBER_LENGTH`
- `workOrderNumber`
- `vehicle`
- `type`
- `status`
- `plannedDateRange`
- `notes`

Metodi pubblici principali:

- `open()`
- `scheduled()`
- `schedule()`
- `start()`
- `complete()`
- `cancel()`
- `getWorkOrderNumber()`
- `getVehicle()`
- `getType()`
- `getStatus()`
- `getPlannedDateRange()`
- `getNotes()`

### `VehicleDowntime`

Tipo: `class`.

Classe flotta: modella mezzi, rimorchi, gomme, tecnica o acquisto asset.

Campi principali:

- `vehicleFleetNumber`
- `startedAt`
- `endedAt`
- `partsCost`
- `laborCost`
- `notes`

Metodi pubblici principali:

- `of()`
- `getVehicleFleetNumber()`
- `getStartedAt()`
- `getEndedAt()`
- `getPartsCost()`
- `getLaborCost()`
- `getNotes()`
- `isOpen()`
- `calculateTotalCost()`
- `equals()`
- `hashCode()`
