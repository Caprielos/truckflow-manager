# Package `shipment` — Spedizioni

## Scopo

Richiesta accettata pronta a essere pianificata e movimentata.

## Classi ed enum

| Nome | Tipo | Ruolo sintetico |
|---|---|---|
| `Shipment` | Classe | Classe di dominio del package. |
| `ShipmentRules` | Classe | Classe di regole di business del package. |
| `ShipmentStatus` | Enum | Valori controllati usati dalle regole di dominio. |

## Enum principali

### `ShipmentStatus`

Valori: `CREATED`, `PLANNED`, `DISPATCHED`, `IN_TRANSIT`, `DELIVERED`, `CANCELLED`.


## Spedizione

La spedizione rappresenta la merce accettata da movimentare.

È distinta dalla missione:

```text
Shipment -> cosa bisogna trasportare
TransportMission -> viaggio operativo con mezzo e autista
```


## Collegamenti con altri package

Questo package non vive isolato: le sue classi vengono usate dalle regole di business e dai casi d’uso futuri.

Per capire il flusso completo leggere anche:

- [`../domain-overview.md`](../domain-overview.md)
- [`../domain-rules.md`](../domain-rules.md)
- [`../domain-package-map.md`](../domain-package-map.md)

## Test collegati

I test si trovano sotto:

```text
src/test/java/it/gabriele/truckflow/domain/shipment
```

Quando si modifica questo package, eseguire sempre:

```bash
mvn clean test
```
