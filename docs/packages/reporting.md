# Package `reporting` — Reportistica

## Scopo

Definizioni report, metriche e report generati.

## Classi ed enum

| Nome | Tipo | Ruolo sintetico |
|---|---|---|
| `GeneratedReport` | Classe | Classe di dominio del package. |
| `ReportDefinition` | Classe | Classe di dominio del package. |
| `ReportFormat` | Enum | Valori controllati usati dalle regole di dominio. |
| `ReportMetric` | Classe | Classe di dominio del package. |
| `ReportMetricType` | Enum | Valori controllati usati dalle regole di dominio. |
| `ReportStatus` | Enum | Valori controllati usati dalle regole di dominio. |
| `ReportType` | Enum | Valori controllati usati dalle regole di dominio. |
| `ReportingRules` | Classe | Classe di regole di business del package. |

## Enum principali

### `ReportFormat`

Valori: `PDF`, `CSV`, `XLSX`, `JSON`, `HTML`.

### `ReportMetricType`

Valori: `SHIPMENT_COUNT`, `COMPLETED_SHIPMENT_COUNT`, `DELAY_COUNT`, `CLAIM_COUNT`, `DOCUMENT_EXPIRATION_COUNT`, `MAINTENANCE_COUNT`, `TOTAL_DISTANCE_KM`, `TOTAL_REVENUE`, `TOTAL_COST`, `TOTAL_CO2_KG`, `VEHICLE_UTILIZATION_PERCENTAGE`, `ON_TIME_DELIVERY_PERCENTAGE`.

### `ReportStatus`

Valori: `DRAFT`, `GENERATED`, `PUBLISHED`, `ARCHIVED`, `FAILED`.

### `ReportType`

Valori: `OPERATIONS`, `FINANCIAL`, `FLEET`, `DRIVER`, `CUSTOMER`, `SUSTAINABILITY`, `COMPLIANCE`, `CLAIMS`, `DOCUMENTS`.



## Collegamenti con altri package

Questo package non vive isolato: le sue classi vengono usate dalle regole di business e dai casi d’uso futuri.

Per capire il flusso completo leggere anche:

- [`../domain-overview.md`](../domain-overview.md)
- [`../domain-rules.md`](../domain-rules.md)
- [`../domain-package-map.md`](../domain-package-map.md)

## Test collegati

I test si trovano sotto:

```text
src/test/java/it/gabriele/truckflow/domain/reporting
```

Quando si modifica questo package, eseguire sempre:

```bash
mvn clean test
```
