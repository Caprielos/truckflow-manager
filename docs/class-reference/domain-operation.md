# Domain `operation` spiegato

Missione operativa reale: autista, convoglio, rotta e stati missione.

## Classi principali

### `TransportMission`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_MISSION_NUMBER_LENGTH`
- `missionNumber`
- `shipment`
- `driver`
- `vehicleCombination`
- `routePlan`
- `status`
- `notes`

Metodi pubblici principali:

- `planned()`
- `dispatch()`
- `start()`
- `complete()`
- `cancel()`
- `getMissionNumber()`
- `getShipment()`
- `getDriver()`
- `getVehicleCombination()`
- `getRoutePlan()`
- `getStatus()`
- `getNotes()`

### `TransportMissionRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `isCompliant()`
- `canBeDispatched()`
- `canBeStarted()`
- `canBeCompleted()`
- `canBeCancelled()`
- `isCompleted()`
- `isTerminal()`
- `requiresSpecialHandling()`

### `TransportMissionStatus`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `terminal`

Metodi pubblici principali:

- `isTerminal()`
