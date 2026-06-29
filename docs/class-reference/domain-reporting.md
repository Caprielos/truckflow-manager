# Package `domain.reporting`

Report generati, definizioni, metriche e stato report.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| GeneratedReport | class | Classe del package domain.reporting; rappresenta un concetto del modello TruckFlow. | draft, generated, generate, publish, archive, fail, getReportNumber, getDefinition, getMetrics, getStatus |
| ReportDefinition | class | Classe del package domain.reporting; rappresenta un concetto del modello TruckFlow. | of, getReportCode, getType, getFormat, getPeriod, getRequestedByAccountId, getNotes, isFinancialReport, isComplianceReport, isSustainabilityReport |
| ReportFormat | enum | Enum: insieme chiuso di valori ammessi dal dominio. | isDownloadable, isMachineReadable |
| ReportMetric | class | Classe del package domain.reporting; rappresenta un concetto del modello TruckFlow. | of, ofDefaultUnit, getMetricCode, getType, getLabel, getValue, getUnit, getNotes, isMonetary, isPercentage |
| ReportMetricType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | isMonetary, isPercentage, getDefaultUnit |
| ReportStatus | enum | Enum di stato del ciclo di vita. | isTerminal, isReadable |
| ReportType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | isOperationalReport, isFinancialReport, isComplianceReport, isSustainabilityReport, requiresRestrictedAccess |
| ReportingRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.reporting. | canBeGenerated, canBePublished, canBeArchived, canBeFailed, isReadyForPublication, containsFinancialMetrics, containsSustainabilityMetrics, requiresRestrictedAccess, containsMetricType, calculateMetricTotal |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
