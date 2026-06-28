# Package `sustainability` — Sostenibilità

## Scopo

Stime emissioni, rating e standard ambientali.

## Classi ed enum

| Nome | Tipo | Ruolo sintetico |
|---|---|---|
| `EmissionEstimate` | Classe | Classe di dominio del package. |
| `EmissionRating` | Enum | Valori controllati usati dalle regole di dominio. |
| `EmissionStandard` | Enum | Valori controllati usati dalle regole di dominio. |
| `FuelType` | Enum | Valori controllati usati dalle regole di dominio. |
| `SustainabilityRules` | Classe | Classe di regole di business del package. |

## Enum principali

### `EmissionRating`

Valori: `LOW`, `MEDIUM`, `HIGH`, `VERY_HIGH`.

### `EmissionStandard`

Valori: `EURO_0`, `EURO_1`, `EURO_2`, `EURO_3`, `EURO_4`, `EURO_5`, `EURO_6`, `ZERO_EMISSION`, `UNKNOWN`.

### `FuelType`

Valori: `DIESEL`, `HVO`, `LNG`, `CNG`, `ELECTRIC`, `HYDROGEN`, `UNKNOWN`.



## Collegamenti con altri package

Questo package non vive isolato: le sue classi vengono usate dalle regole di business e dai casi d’uso futuri.

Per capire il flusso completo leggere anche:

- [`../domain-overview.md`](../domain-overview.md)
- [`../domain-rules.md`](../domain-rules.md)
- [`../domain-package-map.md`](../domain-package-map.md)

## Test collegati

I test si trovano sotto:

```text
src/test/java/it/gabriele/truckflow/domain/sustainability
```

Quando si modifica questo package, eseguire sempre:

```bash
mvn clean test
```
