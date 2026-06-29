# `domain/reporting`

Report generati, metriche e regole reporting.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `GeneratedReport` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_REPORT_NUMBER_LENGTH, reportNumber, definition, metrics, status, generatedAt, notes, requiresGeneratedAt | draft, generated, generate, publish, archive, fail, getReportNumber, getDefinition |
| `ReportDefinition` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_CODE_LENGTH, reportCode, type, format, period, requestedByAccountId, notes | of, getReportCode, getType, getFormat, getPeriod, getRequestedByAccountId, getNotes, isFinancialReport |
| `ReportFormat` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | downloadable, machineReadable | isDownloadable, isMachineReadable |
| `ReportMetric` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_CODE_LENGTH, MAX_LABEL_LENGTH, MAX_UNIT_LENGTH, metricCode, type, label, value, unit | of, ofDefaultUnit, getMetricCode, getType, getLabel, getValue, getUnit, getNotes |
| `ReportMetricType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | monetary, percentage, defaultUnit | isMonetary, isPercentage, getDefaultUnit |
| `ReportStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | terminal, readable | isTerminal, isReadable |
| `ReportType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | operationalReport, financialReport, complianceReport, sustainabilityReport | isOperationalReport, isFinancialReport, isComplianceReport, isSustainabilityReport, requiresRestrictedAccess |
| `ReportingRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | canBeGenerated, canBePublished, canBeArchived, canBeFailed, isReadyForPublication, containsFinancialMetrics, containsSustainabilityMetrics, requiresRestrictedAccess |
