# Package `reporting` — Reportistica

Definisce report, metriche, formato e stato di generazione per viste operative, economiche e compliance.

## Responsabilità

- ReportDefinition definisce cosa generare.
- GeneratedReport rappresenta output prodotto o fallito.

## Classi

- `GeneratedReport` — modello/domain object del package.
- `ReportDefinition` — modello/domain object del package.
- `ReportFormat` — enum con valori: `PDF`, `CSV`, `XLSX`, `JSON`, `HTML`.
- `ReportMetric` — modello/domain object del package.
- `ReportMetricType` — enum con valori: `SHIPMENT_COUNT`, `COMPLETED_SHIPMENT_COUNT`, `DELAY_COUNT`, `CLAIM_COUNT`, `DOCUMENT_EXPIRATION_COUNT`, `MAINTENANCE_COUNT`, `TOTAL_DISTANCE_KM`, `TOTAL_REVENUE`, `TOTAL_COST`, `TOTAL_CO2_KG`, `VEHICLE_UTILIZATION_PERCENTAGE`, `ON_TIME_DELIVERY_PERCENTAGE`.
- `ReportStatus` — enum con valori: `DRAFT`, `GENERATED`, `PUBLISHED`, `ARCHIVED`, `FAILED`.
- `ReportType` — enum con valori: `OPERATIONS`, `FINANCIAL`, `FLEET`, `DRIVER`, `CUSTOMER`, `SUSTAINABILITY`, `COMPLIANCE`, `CLAIMS`, `DOCUMENTS`.
- `ReportingRules` — classe di regole pure del package.

## Collegamenti

- ReportDefinition definisce cosa generare.
- GeneratedReport rappresenta output prodotto o fallito.
