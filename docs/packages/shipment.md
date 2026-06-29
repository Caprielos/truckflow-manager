# Package `shipment` — Spedizione

Rappresenta la spedizione nata da un ordine accettato, con stato e regole; non contiene direttamente driver e mezzo.

## Percorso

```text
src/main/java/it/gabriele/truckflow/domain/shipment
```

## Classi

- `Shipment`
- `ShipmentRules`
- `ShipmentStatus`

## Test collegati

- `ShipmentRulesTest`
- `ShipmentTest`

## Ruolo nel sistema

Questo package contribuisce al domain model e deve restare indipendente da database, controller REST e framework esterni.
