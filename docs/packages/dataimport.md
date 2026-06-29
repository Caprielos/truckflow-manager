# Package `dataimport` — Import dati esterni

Rappresenta batch e record importati da carburante, pedaggi, telematica, banca, paghe o fatture fornitore.

## Percorso

```text
src/main/java/it/gabriele/truckflow/domain/dataimport
```

## Classi

- `ExternalDataSourceType`
- `ImportBatch`
- `ImportRecord`
- `ImportRecordStatus`
- `ImportRules`

## Test collegati

- `DataImportModelTest`

## Ruolo nel sistema

Questo package contribuisce al domain model e deve restare indipendente da database, controller REST e framework esterni.
