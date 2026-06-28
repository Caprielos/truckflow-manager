# Package `operation` — Missioni operative

## Scopo

Il viaggio reale assegnato a carico, mezzo, autista e pianificazione.

## Classi ed enum

| Nome | Tipo | Ruolo sintetico |
|---|---|---|
| `TransportMission` | Classe | Classe di dominio del package. |
| `TransportMissionRules` | Classe | Classe di regole di business del package. |
| `TransportMissionStatus` | Enum | Valori controllati usati dalle regole di dominio. |

## Enum principali

### `TransportMissionStatus`

Valori: `PLANNED`, `DISPATCHED`, `IN_PROGRESS`, `COMPLETED`, `CANCELLED`.


## Missione operativa

La missione è il viaggio reale.

Collega:

- spedizione;
- convoglio;
- autista;
- rotta;
- stato operativo;
- tracking;
- documenti;
- costi.

Non va confusa con l’ordine commerciale o con la spedizione.


## Collegamenti con altri package

Questo package non vive isolato: le sue classi vengono usate dalle regole di business e dai casi d’uso futuri.

Per capire il flusso completo leggere anche:

- [`../domain-overview.md`](../domain-overview.md)
- [`../domain-rules.md`](../domain-rules.md)
- [`../domain-package-map.md`](../domain-package-map.md)

## Test collegati

I test si trovano sotto:

```text
src/test/java/it/gabriele/truckflow/domain/operation
```

Quando si modifica questo package, eseguire sempre:

```bash
mvn clean test
```
