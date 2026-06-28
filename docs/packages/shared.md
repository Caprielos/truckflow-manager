# Package `shared` — Value object condivisi

## Scopo

Oggetti riutilizzabili come peso, distanza, volume, money, date e time window.

## Classi ed enum

| Nome | Tipo | Ruolo sintetico |
|---|---|---|
| `DateRange` | Classe | Classe di dominio del package. |
| `Dimension` | Classe | Classe di dominio del package. |
| `Distance` | Classe | Classe di dominio del package. |
| `Money` | Classe | Classe di dominio del package. |
| `Notes` | Classe | Classe di dominio del package. |
| `Percentage` | Classe | Classe di dominio del package. |
| `TemperatureRange` | Classe | Classe di dominio del package. |
| `TimeWindow` | Classe | Classe di dominio del package. |
| `Volume` | Classe | Classe di dominio del package. |
| `Weight` | Classe | Classe di dominio del package. |

## Enum principali

In questo package non ci sono enum principali.


## Value object condivisi

Questi oggetti proteggono unità di misura e valori fondamentali.

Esempi:

```text
Weight -> kg/tons
Distance -> km/metri
Volume -> m3/litri
Dimension -> metri
Money -> importo + valuta
Percentage -> percentuale
TemperatureRange -> min/max Celsius
DateRange -> intervallo date
TimeWindow -> finestra oraria
Notes -> note validate
```

Usarli evita di passare `double` o `String` senza significato nel domain.


## Collegamenti con altri package

Questo package non vive isolato: le sue classi vengono usate dalle regole di business e dai casi d’uso futuri.

Per capire il flusso completo leggere anche:

- [`../domain-overview.md`](../domain-overview.md)
- [`../domain-rules.md`](../domain-rules.md)
- [`../domain-package-map.md`](../domain-package-map.md)

## Test collegati

I test si trovano sotto:

```text
src/test/java/it/gabriele/truckflow/domain/shared
```

Quando si modifica questo package, eseguire sempre:

```bash
mvn clean test
```
