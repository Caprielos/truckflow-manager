# Package `domain.dataimport`

Import da fonti esterne: carte carburante, pedaggi, telematica, banca, fatture, paghe.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| ExternalDataSourceType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | usuallyContainsEconomicData |
| ImportBatch | class | Classe del package domain.dataimport; rappresenta un concetto del modello TruckFlow. | of, getBatchCode, getSourceType, getImportedAt, getRecords, getNotes, countValidatedRecords, countRejectedRecords, hasRejectedRecords, calculateAmountTotal |
| ImportRecord | class | Classe del package domain.dataimport; rappresenta un concetto del modello TruckFlow. | of, getRecordCode, getSourceType, getExternalRowId, getStatus, getReferenceNumber, getAmount, getOccurredAt, getNotes, hasAmount |
| ImportRecordStatus | enum | Enum di stato del ciclo di vita. | isTerminal, canBePosted |
| ImportRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.dataimport. | batchCanBePostedToDomain, batchRequiresManualReview, sourceUsuallyCreatesEconomicEntries |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
