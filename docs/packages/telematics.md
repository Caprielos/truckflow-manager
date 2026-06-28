# Package `telematics` — Telematica e CAN bus

Modella snapshot GPS/CAN-bus e anomalie di guida: velocità, fuel drop, frenate brusche, idling.

## Responsabilità

- TelematicsSnapshot rappresenta posizione, odometro, carburante e dati CAN.
- TelematicsRules intercetta fuel drop e speeding.

## Classi

- `DrivingBehaviorEvent` — modello/domain object del package.
- `DrivingBehaviorEventType` — enum con valori: `HARSH_BRAKING`, `HARSH_ACCELERATION`, `SPEEDING`, `IDLING_TOO_LONG`, `LOW_FUEL_LEVEL`, `POSSIBLE_FUEL_THEFT`, `ENGINE_FAULT`.
- `TelematicsRules` — classe di regole pure del package.
- `TelematicsSnapshot` — modello/domain object del package.

## Regole importanti

- Fuel drop e speeding sono eventi rilevabili da regole pure.
- Snapshot e behavior event restano separati da mission/tracking.

## Collegamenti

- TelematicsSnapshot rappresenta posizione, odometro, carburante e dati CAN.
- TelematicsRules intercetta fuel drop e speeding.
