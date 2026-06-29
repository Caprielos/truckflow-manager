# `domain/inventory`

Magazzino: ricambi, DPI, gomme, AdBlue, scorte, movimenti e riordino.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `InventoryBalance` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | item, location, availableQuantity | of, fromMovements, getItem, getLocation, getAvailableQuantity, isBelowMinimumStock, calculateStockValue, canReserve |
| `InventoryItem` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_CODE_LENGTH, MAX_DESCRIPTION_LENGTH, MAX_UNIT_LENGTH, itemCode, type, description, unitOfMeasure, unitCost | of, getItemCode, getType, getDescription, getUnitOfMeasure, getUnitCost, getMinimumStockQuantity, getNotes |
| `InventoryItemType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | isSafetyCritical |
| `InventoryRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | canReserve, shouldReorder, safetyCriticalItemShouldHaveStock |
| `InventoryStockMovement` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_CODE_LENGTH, movementCode, itemCode, location, type, quantity, unitCost, occurredAt | of, getMovementCode, getItemCode, getLocation, getType, getQuantity, getUnitCost, getOccurredAt |
| `StockMovementType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | sign | getSign, increasesStock, decreasesStock |
| `WarehouseLocation` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_CODE_LENGTH, facilityCode, zoneCode, shelfCode, binCode | of, getFacilityCode, getZoneCode, getShelfCode, getBinCode, getFullCode, equals, hashCode |
