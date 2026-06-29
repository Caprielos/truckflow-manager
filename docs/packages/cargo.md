# Package `cargo` — Merce e requisiti di carico

Descrive il carico, categorie merce, ADR, temperatura, rifiuti, animali vivi, alimentare e regole operative richieste dalla merce.

## Percorso

```text
src/main/java/it/gabriele/truckflow/domain/cargo
```

## Classi

- `AdrClass`
- `CargoCategory`
- `CargoItem`
- `CargoLoad`
- `CargoLoadRules`
- `CargoOperationalRules`
- `DangerousGoodsProfile`
- `HazardLabel`
- `PackingGroup`

## Test collegati

- `CargoItemTest`
- `CargoLoadRulesTest`
- `CargoLoadTest`
- `CargoOperationalRulesTest`
- `DangerousCargoTest`
- `DangerousGoodsProfileTest`

## Ruolo nel sistema

Questo package contribuisce al domain model e deve restare indipendente da database, controller REST e framework esterni.
