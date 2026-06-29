# `domain/shipment`

Spedizione nata da ordine accettato: cosa deve essere trasportato e stato logistico.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `Shipment` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_SHIPMENT_NUMBER_LENGTH, shipmentNumber, transportOrder, status, notes | fromAcceptedOrder, getShipmentNumber, getTransportOrder, getStatus, getNotes, getCustomerAccount, getCargoLoad, getPickupFacility |
| `ShipmentRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | canBePlanned, canBeDispatched, canBeMarkedInTransit, canBeDelivered, canBeCancelled, requiresSpecialHandling, isCompleted, isTerminal |
| `ShipmentStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | terminal | isTerminal |
