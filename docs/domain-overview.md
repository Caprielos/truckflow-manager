# Domain overview

Il domain contiene le regole e i concetti principali del sistema.

Il domain non deve sapere nulla di:

- database;
- REST API;
- Spring;
- file system;
- web;
- repository concreti.

È composto da molti package specializzati.

## Macro aree

```text
commerciale
→ customer, order, shipment, contract, pricing, billing

operativa
→ operation, route, dispatch, tracking, document, loadsecurity

flotta
→ fleet, tire, fuel, maintenance, telematics, parking, facility

persone
→ driver, payroll, identity

economica
→ economics, billing, pricing, payroll, facility

supporto
→ audit, notification, reporting, configuration, dataimport, inventory
```
