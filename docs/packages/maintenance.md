# Package `maintenance` — Manutenzione e fermi mezzo

Gestisce work order, difetti segnalati dall’autista, fermi mezzo, stati e tipi manutenzione.

## Percorso

```text
src/main/java/it/gabriele/truckflow/domain/maintenance
```

## Classi

- `DriverDefectTicket`
- `MaintenanceRules`
- `MaintenanceStatus`
- `MaintenanceType`
- `MaintenanceWorkOrder`
- `VehicleDowntime`

## Test collegati

- `MaintenanceRulesTest`
- `MaintenanceWorkOrderTest`

## Ruolo nel sistema

Questo package contribuisce al domain model e deve restare indipendente da database, controller REST e framework esterni.
