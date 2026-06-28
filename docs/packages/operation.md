# Package `operation` — Missioni operative

Rappresenta l’esecuzione reale: spedizione + route plan + convoglio + autista + stato operativo.

## Responsabilità

- TransportMission combina Shipment, RoutePlan, VehicleCombination e Driver.
- È il punto operativo dove la spedizione diventa viaggio reale.

## Classi

- `TransportMission` — modello/domain object del package.
- `TransportMissionRules` — classe di regole pure del package.
- `TransportMissionStatus` — enum con valori: `PLANNED`, `DISPATCHED`, `IN_PROGRESS`, `COMPLETED`, `CANCELLED`.

## Collegamenti

- TransportMission combina Shipment, RoutePlan, VehicleCombination e Driver.
- È il punto operativo dove la spedizione diventa viaggio reale.
