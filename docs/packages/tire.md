# Package `tire` — Gestione pneumatici

## Scopo

Gomme singole, posizioni ruota e ciclo vita pneumatici.

## Classi ed enum

| Nome | Tipo | Ruolo sintetico |
|---|---|---|
| `Tire` | Classe | Singola gomma fisica, tracciabile separatamente dal veicolo. |
| `TireStatus` | Enum | Valori controllati usati dalle regole di dominio. |
| `WheelPosition` | Classe | Posizione ruota sul mezzo. |
| `WheelSide` | Enum | Lato della ruota. |
| `WheelSlot` | Enum | Slot ruota: singola, interna o esterna. |

## Enum principali

### `TireStatus`

Valori: `NEW`, `RETREADED`, `REGROOVED`, `IN_USE`, `STORED`, `DISPOSED`.

### `WheelSide`

Valori: `LEFT`, `RIGHT`, `CENTER`.

### `WheelSlot`

Valori: `SINGLE`, `INNER`, `OUTER`.


## Gestione gomme singole

Nel trasporto pesante le gomme si gestiscono una per una.

Il package modella:

- singola gomma (`Tire`);
- stato: nuova, ricostruita, riscolpita, in uso, in magazzino, dismessa;
- posizione ruota;
- lato e slot interno/esterno.

In futuro si potrà aggiungere storico rotazioni, misurazioni battistrada e alert usura.


## Collegamenti con altri package

Questo package non vive isolato: le sue classi vengono usate dalle regole di business e dai casi d’uso futuri.

Per capire il flusso completo leggere anche:

- [`../domain-overview.md`](../domain-overview.md)
- [`../domain-rules.md`](../domain-rules.md)
- [`../domain-package-map.md`](../domain-package-map.md)

## Test collegati

I test si trovano sotto:

```text
src/test/java/it/gabriele/truckflow/domain/tire
```

Quando si modifica questo package, eseguire sempre:

```bash
mvn clean test
```
