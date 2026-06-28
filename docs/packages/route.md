# Package `route` — Piani di viaggio

Modella soste, pickup, delivery, pause, carburante e sequenza di viaggio.

## Responsabilità

- RoutePlan entra nella TransportMission.
- RouteStop permette pickup, delivery, fuel stop, rest break e fine viaggio.

## Classi

- `RoutePlan` — modello/domain object del package.
- `RoutePlanRules` — classe di regole pure del package.
- `RouteStop` — modello/domain object del package.
- `RouteStopType` — enum con valori: `START`, `PICKUP`, `DELIVERY`, `REST_BREAK`, `FUEL_STOP`, `END`.

## Collegamenti

- RoutePlan entra nella TransportMission.
- RouteStop permette pickup, delivery, fuel stop, rest break e fine viaggio.
