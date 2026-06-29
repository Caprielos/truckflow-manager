# Domain `reporting` spiegato

Report generati, metriche e regole reporting.

## Classi principali

### `GeneratedReport`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_REPORT_NUMBER_LENGTH`
- `reportNumber`
- `definition`
- `metrics`
- `status`
- `generatedAt`
- `notes`
- `requiresGeneratedAt`

Metodi pubblici principali:

- `draft()`
- `generated()`
- `generate()`
- `publish()`
- `archive()`
- `fail()`
- `getReportNumber()`
- `getDefinition()`
- `getMetrics()`
- `getStatus()`
- `getGeneratedAt()`
- `getNotes()`

### `ReportDefinition`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_CODE_LENGTH`
- `reportCode`
- `type`
- `format`
- `period`
- `requestedByAccountId`
- `notes`

Metodi pubblici principali:

- `of()`
- `getReportCode()`
- `getType()`
- `getFormat()`
- `getPeriod()`
- `getRequestedByAccountId()`
- `getNotes()`
- `isFinancialReport()`
- `isComplianceReport()`
- `isSustainabilityReport()`
- `requiresRestrictedAccess()`
- `isDownloadable()`

### `ReportFormat`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `downloadable`
- `machineReadable`

Metodi pubblici principali:

- `isDownloadable()`
- `isMachineReadable()`

### `ReportMetric`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_CODE_LENGTH`
- `MAX_LABEL_LENGTH`
- `MAX_UNIT_LENGTH`
- `metricCode`
- `type`
- `label`
- `value`
- `unit`
- `notes`

Metodi pubblici principali:

- `of()`
- `ofDefaultUnit()`
- `getMetricCode()`
- `getType()`
- `getLabel()`
- `getValue()`
- `getUnit()`
- `getNotes()`
- `isMonetary()`
- `isPercentage()`
- `hasNotes()`
- `formatSingleLine()`

### `ReportMetricType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `monetary`
- `percentage`
- `defaultUnit`

Metodi pubblici principali:

- `isMonetary()`
- `isPercentage()`
- `getDefaultUnit()`

### `ReportStatus`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `terminal`
- `readable`

Metodi pubblici principali:

- `isTerminal()`
- `isReadable()`

### `ReportType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `operationalReport`
- `financialReport`
- `complianceReport`
- `sustainabilityReport`

Metodi pubblici principali:

- `isOperationalReport()`
- `isFinancialReport()`
- `isComplianceReport()`
- `isSustainabilityReport()`
- `requiresRestrictedAccess()`

### `ReportingRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `canBeGenerated()`
- `canBePublished()`
- `canBeArchived()`
- `canBeFailed()`
- `isReadyForPublication()`
- `containsFinancialMetrics()`
- `containsSustainabilityMetrics()`
- `requiresRestrictedAccess()`
- `containsMetricType()`
- `calculateMetricTotal()`
