# Package `payroll` — Costo autista e stipendio missione

Calcola voci paga in base a ore, patenti, ADR, rimorchio, trasporto speciale, straordinari, trasferte e costo aziendale.

## Percorso

```text
src/main/java/it/gabriele/truckflow/domain/payroll
```

## Classi

- `DriverMissionPayLine`
- `DriverMissionPayroll`
- `DriverMissionWorkReport`
- `DriverPayComponentType`
- `DriverPayRule`
- `DriverPayUnit`
- `DriverPayrollPolicy`
- `DriverPayrollRules`
- `MissionPayrollProjection`

## Test collegati

- `DriverPayrollRulesTest`

## Ruolo nel sistema

Questo package contribuisce al domain model e deve restare indipendente da database, controller REST e framework esterni.
