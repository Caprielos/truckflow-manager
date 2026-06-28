# Package `shipment` — Spedizioni

Rappresenta la spedizione generata da un ordine accettato. È il collegamento commerciale/logistico tra ordine e missione operativa.

## Responsabilità

- Shipment nasce solo da TransportOrder ACCEPTED.
- Shipment rimane commerciale/logistica; TransportMission gestisce driver, mezzo e rotta reale.
- Il vecchio package fuori da domain è stato eliminato: ora esiste una sola sorgente corretta.

## Classi

- `Shipment` — modello/domain object del package.
- `ShipmentRules` — classe di regole pure del package.
- `ShipmentStatus` — enum con valori: `CREATED`, `PLANNED`, `DISPATCHED`, `IN_TRANSIT`, `DELIVERED`, `CANCELLED`.

## Regole importanti

- Una Shipment può essere creata solo da un TransportOrder ACCEPTED.
- Transizioni valide: CREATED → PLANNED → DISPATCHED → IN_TRANSIT → DELIVERED.
- CANCELLED è consentito solo se la spedizione non è terminale.

## Collegamenti

- Shipment nasce solo da TransportOrder ACCEPTED.
- Shipment rimane commerciale/logistica; TransportMission gestisce driver, mezzo e rotta reale.
- Il vecchio package fuori da domain è stato eliminato: ora esiste una sola sorgente corretta.
