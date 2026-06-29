# Domain `loadsecurity` spiegato

Fissaggio carico, attrezzature e checklist sicurezza carico.

## Classi principali

### `LoadSecuringChecklist`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `equipment`

Metodi pubblici principali:

- `of()`
- `getEquipment()`
- `countByType()`
- `hasAtLeast()`

### `LoadSecuringEquipment`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `type`
- `quantity`
- `capacityDan`

Metodi pubblici principali:

- `of()`
- `getType()`
- `getQuantity()`
- `getCapacityDan()`
- `totalCapacityDan()`
- `equals()`
- `hashCode()`

### `LoadSecuringEquipmentType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

### `LoadSecuringRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `hasMinimumEquipmentForCargo()`
- `estimateMinimumStraps()`
