# Package `inventory` — Magazzino ricambi e materiali

Gestisce articoli, giacenze, ubicazioni, movimenti stock, scorte minime e reorder signal.

## Percorso

```text
src/main/java/it/gabriele/truckflow/domain/inventory
```

## Classi

- `InventoryBalance`
- `InventoryItem`
- `InventoryItemType`
- `InventoryRules`
- `InventoryStockMovement`
- `StockMovementType`
- `WarehouseLocation`

## Test collegati

- `InventoryManagementTest`

## Ruolo nel sistema

Questo package contribuisce al domain model e deve restare indipendente da database, controller REST e framework esterni.
