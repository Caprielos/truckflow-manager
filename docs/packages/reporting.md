# Package `reporting` — Reportistica domain

Modella definizioni report, metriche, formato, stato e regole di generazione logica.

## Percorso

```text
src/main/java/it/gabriele/truckflow/domain/reporting
```

## Classi

- `GeneratedReport`
- `ReportDefinition`
- `ReportFormat`
- `ReportMetric`
- `ReportMetricType`
- `ReportStatus`
- `ReportType`
- `ReportingRules`

## Test collegati

- `GeneratedReportTest`
- `ReportingRulesTest`

## Ruolo nel sistema

Questo package contribuisce al domain model e deve restare indipendente da database, controller REST e framework esterni.
