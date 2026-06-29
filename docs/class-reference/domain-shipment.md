# Domain `shipment` spiegato

Spedizione nata da ordine accettato: cosa deve essere trasportato e stato logistico.

## Classi principali

### `Shipment`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_SHIPMENT_NUMBER_LENGTH`
- `shipmentNumber`
- `transportOrder`
- `status`
- `notes`

Metodi pubblici principali:

- `fromAcceptedOrder()`
- `getShipmentNumber()`
- `getTransportOrder()`
- `getStatus()`
- `getNotes()`
- `getCustomerAccount()`
- `getCargoLoad()`
- `getPickupFacility()`
- `getDeliveryFacility()`
- `isInternational()`
- `requiresTemperatureControlledTransport()`
- `containsHazardousMaterial()`

### `ShipmentRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `canBePlanned()`
- `canBeDispatched()`
- `canBeMarkedInTransit()`
- `canBeDelivered()`
- `canBeCancelled()`
- `requiresSpecialHandling()`
- `isCompleted()`
- `isTerminal()`

### `ShipmentStatus`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `terminal`

Metodi pubblici principali:

- `isTerminal()`
