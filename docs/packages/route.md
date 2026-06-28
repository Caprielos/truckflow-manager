# Package `route` — Rotte e tappe

## Scopo

Piani di rotta con stop, sequenza e regole operative.

## Classi ed enum

| Nome | Tipo | Ruolo sintetico |
|---|---|---|
| `RoutePlan` | Classe | Classe di dominio del package. |
| `RoutePlanRules` | Classe | Classe di regole di business del package. |
| `RouteStop` | Classe | Classe di dominio del package. |
| `RouteStopType` | Enum | Valori controllati usati dalle regole di dominio. |

## Enum principali

### `RouteStopType`

Valori: `START`, `PICKUP`, `DELIVERY`, `REST_BREAK`, `FUEL_STOP`, `END`.



## Collegamenti con altri package

Questo package non vive isolato: le sue classi vengono usate dalle regole di business e dai casi d’uso futuri.

Per capire il flusso completo leggere anche:

- [`../domain-overview.md`](../domain-overview.md)
- [`../domain-rules.md`](../domain-rules.md)
- [`../domain-package-map.md`](../domain-package-map.md)

## Test collegati

I test si trovano sotto:

```text
src/test/java/it/gabriele/truckflow/domain/route
```

Quando si modifica questo package, eseguire sempre:

```bash
mvn clean test
```
