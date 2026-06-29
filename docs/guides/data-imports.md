# Data imports

## Dove si trova

```text
src/main/java/it/gabriele/truckflow/domain/dataimport
```

## Obiettivo

Rappresentare dati esterni importati da sistemi reali.

Esempi:

- carte carburante;
- pedaggi;
- telematica GPS;
- banca;
- fatture fornitori;
- paghe;
- tachigrafo;
- manutenzione esterna.

## Classi principali

```text
ImportBatch
ImportRecord
ImportRecordStatus
ExternalDataSourceType
ImportRules
```

## Nota architetturale

Il domain rappresenta il risultato logico dell'import. La lettura vera di CSV, Excel, API o file starà in infrastructure.
