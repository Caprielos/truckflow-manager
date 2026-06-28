# Package `tracking` — Timeline eventi

Registra eventi di viaggio: partenza, arrivo, pickup, delivery, ritardi, incidenti, snapshot telematici.

## Responsabilità

- TrackingTimeline conserva sequenza eventi.
- TrackingEventType include eventi operativi e telematici.

## Classi

- `TrackingEvent` — modello/domain object del package.
- `TrackingEventType` — enum con valori: `POSITION_RECORDED`, `DEPARTED`, `ARRIVED`, `PICKUP_COMPLETED`, `DELIVERY_COMPLETED`, `DELAY_REPORTED`, `INCIDENT_REPORTED`, `MISSION_COMPLETED`, `CAN_BUS_SNAPSHOT`, `HARSH_BRAKING`, `SPEEDING`, `FUEL_LEVEL_RECORDED`.
- `TrackingRules` — classe di regole pure del package.
- `TrackingTimeline` — modello/domain object del package.

## Collegamenti

- TrackingTimeline conserva sequenza eventi.
- TrackingEventType include eventi operativi e telematici.
