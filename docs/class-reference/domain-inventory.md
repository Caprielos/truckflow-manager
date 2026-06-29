# Domain `inventory` spiegato

Magazzino: ricambi, DPI, gomme, AdBlue, scorte, movimenti e riordino.

## Classi principali

### `InventoryBalance`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `item`
- `location`
- `availableQuantity`

Metodi pubblici principali:

- `of()`
- `fromMovements()`
- `getItem()`
- `getLocation()`
- `getAvailableQuantity()`
- `isBelowMinimumStock()`
- `calculateStockValue()`
- `canReserve()`

### `InventoryItem`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_CODE_LENGTH`
- `MAX_DESCRIPTION_LENGTH`
- `MAX_UNIT_LENGTH`
- `itemCode`
- `type`
- `description`
- `unitOfMeasure`
- `unitCost`
- `minimumStockQuantity`
- `notes`

Metodi pubblici principali:

- `of()`
- `getItemCode()`
- `getType()`
- `getDescription()`
- `getUnitOfMeasure()`
- `getUnitCost()`
- `getMinimumStockQuantity()`
- `getNotes()`
- `calculateStockValue()`
- `isBelowMinimumStock()`
- `equals()`
- `hashCode()`

### `InventoryItemType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Metodi pubblici principali:

- `isSafetyCritical()`

### `InventoryRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `canReserve()`
- `shouldReorder()`
- `safetyCriticalItemShouldHaveStock()`

### `InventoryStockMovement`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_CODE_LENGTH`
- `movementCode`
- `itemCode`
- `location`
- `type`
- `quantity`
- `unitCost`
- `occurredAt`
- `referenceNumber`
- `notes`

Metodi pubblici principali:

- `of()`
- `getMovementCode()`
- `getItemCode()`
- `getLocation()`
- `getType()`
- `getQuantity()`
- `getUnitCost()`
- `getOccurredAt()`
- `getReferenceNumber()`
- `getNotes()`
- `signedQuantity()`
- `isRelatedTo()`

### `StockMovementType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `sign`

Metodi pubblici principali:

- `getSign()`
- `increasesStock()`
- `decreasesStock()`

### `WarehouseLocation`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_CODE_LENGTH`
- `facilityCode`
- `zoneCode`
- `shelfCode`
- `binCode`

Metodi pubblici principali:

- `of()`
- `getFacilityCode()`
- `getZoneCode()`
- `getShelfCode()`
- `getBinCode()`
- `getFullCode()`
- `equals()`
- `hashCode()`
