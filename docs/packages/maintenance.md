# Package `maintenance` — Manutenzione e downtime

Gestisce work order, scadenze manutentive, ticket autista, fermo mezzo e tipologie intervento.

## Responsabilità

- MaintenanceWorkOrder registra interventi.
- DriverDefectTicket collega segnalazioni autista alla manutenzione.
- VehicleDowntime misura fermo tecnico.

## Classi

- `DriverDefectTicket` — modello/domain object del package.
- `MaintenanceRules` — classe di regole pure del package.
- `MaintenanceStatus` — enum con valori: `OPEN`, `SCHEDULED`, `IN_PROGRESS`, `COMPLETED`, `CANCELLED`.
- `MaintenanceType` — enum con valori: `ROUTINE_SERVICE`, `SAFETY_INSPECTION`, `TIRE_REPLACEMENT`, `REPAIR`, `REFRIGERATION_UNIT_SERVICE`, `ADR_TANK_INSPECTION`, `BREAKDOWN`, `ENGINE_SERVICE`, `AIR_DRYER_FILTER_REPLACEMENT`, `BRAKE_WEAR_CHECK`, `TIRE_ROTATION`, `DRIVER_DEFECT_TICKET`, `DOWNTIME`.
- `MaintenanceWorkOrder` — modello/domain object del package.
- `VehicleDowntime` — modello/domain object del package.

## Regole importanti

- Work order e downtime sono separati: uno è intervento, l’altro è indisponibilità del mezzo.
- Ticket autista permette segnalazioni dal campo.

## Collegamenti

- MaintenanceWorkOrder registra interventi.
- DriverDefectTicket collega segnalazioni autista alla manutenzione.
- VehicleDowntime misura fermo tecnico.
