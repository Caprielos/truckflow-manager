# Package `reporting` — Reporting

## Scopo

Modella definizioni report, metriche e report generati.

## Concetti principali

- `ReportDefinition`
- `GeneratedReport`
- `ReportMetric`
- `ReportType`
- `ReportFormat`
- `ReportStatus`
- `ReportMetricType`
- `ReportingRules`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `GeneratedReport` | final class | Entity o value object del package. |
| `ReportDefinition` | final class | Entity o value object del package. |
| `ReportFormat` | enum | Enum di classificazione/valori ammessi. |
| `ReportMetric` | final class | Entity o value object del package. |
| `ReportMetricType` | enum | Enum di classificazione/valori ammessi. |
| `ReportStatus` | enum | Enum di classificazione/valori ammessi. |
| `ReportType` | enum | Enum di classificazione/valori ammessi. |
| `ReportingRules` | final class | Classe statica di regole di business del package. |

## Enum e valori ammessi

- `ReportFormat`: `PDF`, `CSV`, `XLSX`, `JSON`, `HTML`
- `ReportMetricType`: `SHIPMENT_COUNT`, `COMPLETED_SHIPMENT_COUNT`, `DELAY_COUNT`, `CLAIM_COUNT`, `DOCUMENT_EXPIRATION_COUNT`, `MAINTENANCE_COUNT`, `TOTAL_DISTANCE_KM`, `TOTAL_REVENUE`, `TOTAL_COST`, `TOTAL_CO2_KG`, `VEHICLE_UTILIZATION_PERCENTAGE`, `ON_TIME_DELIVERY_PERCENTAGE`
- `ReportStatus`: `DRAFT`, `GENERATED`, `PUBLISHED`, `ARCHIVED`, `FAILED`
- `ReportType`: `OPERATIONS`, `FINANCIAL`, `FLEET`, `DRIVER`, `CUSTOMER`, `SUSTAINABILITY`, `COMPLIANCE`, `CLAIMS`, `DOCUMENTS`

## Regole di business

- Report definisce tipo, formato e metriche.
- Report generato segue uno stato e può fallire/completarsi.

## Collegamenti con altri package

- billing, fleet, operation, sustainability, maintenance

## Test collegati

- `GeneratedReportTest.java`
- `ReportingRulesTest.java`

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
