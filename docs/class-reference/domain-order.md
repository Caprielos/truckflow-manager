# Domain `order` spiegato

Ordini di trasporto commerciali prima della spedizione.

## Classi principali

### `TransportOrder`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_ORDER_NUMBER_LENGTH`
- `orderNumber`
- `customerAccount`
- `cargoLoad`
- `pickupFacility`
- `deliveryFacility`
- `pickupWindow`
- `deliveryWindow`
- `serviceType`
- `quotedPrice`
- `status`
- `notes`

Metodi pubblici principali:

- `draft()`
- `submitted()`
- `getOrderNumber()`
- `getCustomerAccount()`
- `getCargoLoad()`
- `getPickupFacility()`
- `getDeliveryFacility()`
- `getPickupWindow()`
- `getDeliveryWindow()`
- `getServiceType()`
- `getQuotedPrice()`
- `getStatus()`

### `TransportOrderStatus`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `terminal`

Metodi pubblici principali:

- `isTerminal()`

### `TransportServiceType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `supportsTemperatureControl`
- `supportsHazardousMaterial`

Metodi pubblici principali:

- `supportsTemperatureControl()`
- `supportsHazardousMaterial()`
