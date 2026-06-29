# Magazzino e inventory

Il package `inventory` gestisce materiali, ricambi e giacenze.

Esempi di articoli:

```text
pastiglie freno
gomme
olio
AdBlue
filtri
DPI
cinghie
attrezzatura ADR
pallet
```

## Oggetti principali

```text
InventoryItem
WarehouseLocation
InventoryStockMovement
InventoryBalance
InventoryRules
```

## Movimenti

Un movimento può rappresentare:

```text
acquisto in ingresso
consumo manutenzione
rettifica
riserva
rientro
```

## Riordino

`InventoryRules.shouldReorder(...)` segnala quando la giacenza scende sotto la soglia minima.
