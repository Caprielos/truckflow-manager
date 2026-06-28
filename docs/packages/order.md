# Package `order` — Ordini di trasporto

Rappresenta la richiesta commerciale accettabile o rifiutabile prima di diventare spedizione.

## Responsabilità

- TransportOrder nasce da cliente, cargo, pickup/delivery, finestre orarie e prezzo.
- Solo un ordine ACCEPTED può generare una Shipment.

## Classi

- `TransportOrder` — modello/domain object del package.
- `TransportOrderStatus` — enum con valori: `DRAFT`, `SUBMITTED`, `ACCEPTED`, `REJECTED`, `CANCELLED`.
- `TransportServiceType` — enum con valori: `STANDARD`, `EXPRESS`, `REFRIGERATED`, `HAZARDOUS`, `OVERSIZED`.

## Collegamenti

- TransportOrder nasce da cliente, cargo, pickup/delivery, finestre orarie e prezzo.
- Solo un ordine ACCEPTED può generare una Shipment.
