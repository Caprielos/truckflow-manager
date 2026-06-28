# Package `telematics` — Telematica

## Scopo

Snapshot GPS/CAN-bus e dati letti da centraline esterne.

## Classi ed enum

| Nome | Tipo | Ruolo sintetico |
|---|---|---|
| `TelematicsSnapshot` | Classe | Fotografia telematica/GPS/CAN-bus del mezzo. |

## Enum principali

In questo package non ci sono enum principali.


## Telematica e GPS

`TelematicsSnapshot` rappresenta una fotografia dati del mezzo:

- coordinate GPS;
- timestamp;
- odometro;
- livello carburante;
- eventi guida.

La chiamata reale alle API GPS starà in infrastructure, non nel domain.


## Collegamenti con altri package

Questo package non vive isolato: le sue classi vengono usate dalle regole di business e dai casi d’uso futuri.

Per capire il flusso completo leggere anche:

- [`../domain-overview.md`](../domain-overview.md)
- [`../domain-rules.md`](../domain-rules.md)
- [`../domain-package-map.md`](../domain-package-map.md)

## Test collegati

I test si trovano sotto:

```text
src/test/java/it/gabriele/truckflow/domain/telematics
```

Quando si modifica questo package, eseguire sempre:

```bash
mvn clean test
```
