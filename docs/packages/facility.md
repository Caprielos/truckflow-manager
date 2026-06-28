# Package `facility` — Sedi operative

Rappresenta magazzini, depositi, clienti, terminal, porti, aeroporti e centri manutenzione.

## Responsabilità

- Order collega pickupFacility e deliveryFacility.
- RoutePlan organizza soste su punti logistici.

## Classi

- `Facility` — modello/domain object del package.
- `FacilityType` — enum con valori: `WAREHOUSE`, `DEPOT`, `CUSTOMER_SITE`, `SUPPLIER_SITE`, `CROSS_DOCK`, `TERMINAL`, `PORT`, `AIRPORT`, `MAINTENANCE_CENTER`.

## Collegamenti

- Order collega pickupFacility e deliveryFacility.
- RoutePlan organizza soste su punti logistici.
