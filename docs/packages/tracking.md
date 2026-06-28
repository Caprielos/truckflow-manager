# Package `tracking` — Tracking

## Scopo

Timeline di eventi di spedizione, missione e mezzo.

## Classi ed enum

| Nome | Tipo | Ruolo sintetico |
|---|---|---|
| `TrackingEvent` | Classe | Classe di dominio del package. |
| `TrackingEventType` | Enum | Valori controllati usati dalle regole di dominio. |
| `TrackingRules` | Classe | Classe di regole di business del package. |
| `TrackingTimeline` | Classe | Classe di dominio del package. |

## Enum principali

### `TrackingEventType`

Valori: `POSITION_RECORDED`, `DEPARTED`, `ARRIVED`, `PICKUP_COMPLETED`, `DELIVERY_COMPLETED`, `DELAY_REPORTED`, `INCIDENT_REPORTED`, `MISSION_COMPLETED`, `CAN_BUS_SNAPSHOT`, `HARSH_BRAKING`, `SPEEDING`, `FUEL_LEVEL_RECORDED`.



## Collegamenti con altri package

Questo package non vive isolato: le sue classi vengono usate dalle regole di business e dai casi d’uso futuri.

Per capire il flusso completo leggere anche:

- [`../domain-overview.md`](../domain-overview.md)
- [`../domain-rules.md`](../domain-rules.md)
- [`../domain-package-map.md`](../domain-package-map.md)

## Test collegati

I test si trovano sotto:

```text
src/test/java/it/gabriele/truckflow/domain/tracking
```

Quando si modifica questo package, eseguire sempre:

```bash
mvn clean test
```
