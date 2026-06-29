# `domain/order`

Ordini di trasporto commerciali prima della spedizione.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `TransportOrder` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_ORDER_NUMBER_LENGTH, orderNumber, customerAccount, cargoLoad, pickupFacility, deliveryFacility, pickupWindow, deliveryWindow | draft, submitted, getOrderNumber, getCustomerAccount, getCargoLoad, getPickupFacility, getDeliveryFacility, getPickupWindow |
| `TransportOrderStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | terminal | isTerminal |
| `TransportServiceType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | supportsTemperatureControl, supportsHazardousMaterial | supportsTemperatureControl, supportsHazardousMaterial |
