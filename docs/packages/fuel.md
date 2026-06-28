# Package `fuel` — Carburante e rifornimenti

## Scopo

Registra rifornimenti, provider carte carburante e calcolo del consumo reale.

## Classi ed enum

| Nome | Tipo | Ruolo sintetico |
|---|---|---|
| `FuelCardProvider` | Enum | Provider carte carburante. |
| `FuelTransaction` | Classe | Rifornimento carburante con litri, prezzo, odometro e provider carta. |

## Enum principali

### `FuelCardProvider`

Valori: `DKV`, `UTA`, `ENI`, `SHELL`, `OTHER`.


## Rifornimenti e consumi

`FuelTransaction` registra ogni rifornimento con:

- mezzo;
- data/ora;
- litri;
- prezzo al litro;
- odometro;
- provider carta carburante.

Il consumo reale viene calcolato confrontando due rifornimenti successivi.


## Collegamenti con altri package

Questo package non vive isolato: le sue classi vengono usate dalle regole di business e dai casi d’uso futuri.

Per capire il flusso completo leggere anche:

- [`../domain-overview.md`](../domain-overview.md)
- [`../domain-rules.md`](../domain-rules.md)
- [`../domain-package-map.md`](../domain-package-map.md)

## Test collegati

I test si trovano sotto:

```text
src/test/java/it/gabriele/truckflow/domain/fuel
```

Quando si modifica questo package, eseguire sempre:

```bash
mvn clean test
```
