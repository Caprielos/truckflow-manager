# `domain/dataimport`

Import da fonti esterne: carte carburante, pedaggi, telematica, banca, paghe e fatture.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `ExternalDataSourceType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | usuallyContainsEconomicData |
| `ImportBatch` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_CODE_LENGTH, batchCode, sourceType, importedAt, records, notes | of, getBatchCode, getSourceType, getImportedAt, getRecords, getNotes, countValidatedRecords, countRejectedRecords |
| `ImportRecord` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_CODE_LENGTH, recordCode, sourceType, externalRowId, status, referenceNumber, amount, occurredAt | of, getRecordCode, getSourceType, getExternalRowId, getStatus, getReferenceNumber, getAmount, getOccurredAt |
| `ImportRecordStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | terminal | isTerminal, canBePosted |
| `ImportRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | batchCanBePostedToDomain, batchRequiresManualReview, sourceUsuallyCreatesEconomicEntries |
