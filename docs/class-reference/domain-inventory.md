# Package `domain.inventory`

Magazzino materiali: ricambi, gomme, DPI, AdBlue, olio, movimenti e riordino.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| InventoryBalance | class | Classe del package domain.inventory; rappresenta un concetto del modello TruckFlow. | of, fromMovements, getItem, getLocation, getAvailableQuantity, isBelowMinimumStock, calculateStockValue, canReserve |
| InventoryItem | class | Classe del package domain.inventory; rappresenta un concetto del modello TruckFlow. | of, getItemCode, getType, getDescription, getUnitOfMeasure, getUnitCost, getMinimumStockQuantity, getNotes, calculateStockValue, isBelowMinimumStock |
| InventoryItemType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | isSafetyCritical |
| InventoryRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.inventory. | canReserve, shouldReorder, safetyCriticalItemShouldHaveStock |
| InventoryStockMovement | class | Classe del package domain.inventory; rappresenta un concetto del modello TruckFlow. | of, getMovementCode, getItemCode, getLocation, getType, getQuantity, getUnitCost, getOccurredAt, getReferenceNumber, getNotes |
| StockMovementType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | getSign, increasesStock, decreasesStock |
| WarehouseLocation | class | Classe del package domain.inventory; rappresenta un concetto del modello TruckFlow. | of, getFacilityCode, getZoneCode, getShelfCode, getBinCode, getFullCode, equals, hashCode |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
