# Domain layer

Il domain contiene il cuore del progetto. Qui non devono esserci database, controller REST, JSON, Spring o filesystem.

## Cosa contiene

- entity e value object
- enum di stato e classificazione
- regole di business
- calcoli puri
- validazioni

## Esempi

- `Shipment` rappresenta la spedizione.
- `TransportMission` rappresenta il viaggio operativo.
- `MissionEconomics` rappresenta costi/ricavi/margine.
- `ParkingAssignment` rappresenta l’occupazione di un posto parcheggio.
- `DriverMissionPayroll` rappresenta il costo autista per missione.

## Perché è importante

Un domain pulito può essere testato senza database e può essere riusato da REST API, CLI, batch o integrazioni future.
