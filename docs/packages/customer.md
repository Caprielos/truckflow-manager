# Package `customer` — Clienti e account

Gestisce cliente, stato commerciale, account operativo e contatti per logistica, amministrazione e fatturazione.

## Responsabilità

- CustomerAccount viene usato da TransportOrder.
- CustomerContact può ricevere notifiche e documenti.

## Classi

- `Customer` — modello/domain object del package.
- `CustomerAccount` — modello/domain object del package.
- `CustomerContact` — modello/domain object del package.
- `CustomerContactRole` — enum con valori: `LOGISTICS`, `ADMINISTRATION`, `BILLING`, `OPERATIONS`, `SALES`, `MANAGEMENT`, `OTHER`.
- `CustomerStatus` — enum con valori: `ACTIVE`, `INACTIVE`, `SUSPENDED`.
- `CustomerType` — enum con valori: `INDIVIDUAL`, `COMPANY`, `PUBLIC_AUTHORITY`, `INTERNAL`.

## Collegamenti

- CustomerAccount viene usato da TransportOrder.
- CustomerContact può ricevere notifiche e documenti.
