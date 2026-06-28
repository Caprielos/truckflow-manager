# Package `drivetime` — Tempi di guida e riposo

## Scopo

Regole base su guida giornaliera, pausa 4h30 e riposo giornaliero.

## Classi ed enum

| Nome | Tipo | Ruolo sintetico |
|---|---|---|
| `DriverTimeRules` | Classe | Regole base su guida, pausa e riposo. |

## Enum principali

In questo package non ci sono enum principali.


## Tempi di guida e riposo

Il package contiene le prime regole operative per pianificare viaggi realistici:

- massimo guida giornaliera;
- estensione a 10 ore limitata;
- pausa 45 minuti ogni 4h30;
- riposo giornaliero.

In futuro questa parte potrà diventare un motore di pianificazione più avanzato.


## Collegamenti con altri package

Questo package non vive isolato: le sue classi vengono usate dalle regole di business e dai casi d’uso futuri.

Per capire il flusso completo leggere anche:

- [`../domain-overview.md`](../domain-overview.md)
- [`../domain-rules.md`](../domain-rules.md)
- [`../domain-package-map.md`](../domain-package-map.md)

## Test collegati

I test si trovano sotto:

```text
src/test/java/it/gabriele/truckflow/domain/drivetime
```

Quando si modifica questo package, eseguire sempre:

```bash
mvn clean test
```
