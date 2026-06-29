# Domain `dataimport` spiegato

Import da fonti esterne: carte carburante, pedaggi, telematica, banca, paghe e fatture.

## Classi principali

### `ExternalDataSourceType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Metodi pubblici principali:

- `usuallyContainsEconomicData()`

### `ImportBatch`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_CODE_LENGTH`
- `batchCode`
- `sourceType`
- `importedAt`
- `records`
- `notes`

Metodi pubblici principali:

- `of()`
- `getBatchCode()`
- `getSourceType()`
- `getImportedAt()`
- `getRecords()`
- `getNotes()`
- `countValidatedRecords()`
- `countRejectedRecords()`
- `hasRejectedRecords()`
- `calculateAmountTotal()`

### `ImportRecord`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_CODE_LENGTH`
- `recordCode`
- `sourceType`
- `externalRowId`
- `status`
- `referenceNumber`
- `amount`
- `occurredAt`
- `notes`

Metodi pubblici principali:

- `of()`
- `getRecordCode()`
- `getSourceType()`
- `getExternalRowId()`
- `getStatus()`
- `getReferenceNumber()`
- `getAmount()`
- `getOccurredAt()`
- `getNotes()`
- `hasAmount()`
- `canBePostedToDomain()`
- `equals()`

### `ImportRecordStatus`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `terminal`

Metodi pubblici principali:

- `isTerminal()`
- `canBePosted()`

### `ImportRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `batchCanBePostedToDomain()`
- `batchRequiresManualReview()`
- `sourceUsuallyCreatesEconomicEntries()`
