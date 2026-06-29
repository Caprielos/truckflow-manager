# Package `domain.shipment`

Spedizione generata da un ordine accettato: merce, origine/destinazione, stato e regole logistiche.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| Shipment | class | Classe del package domain.shipment; rappresenta un concetto del modello TruckFlow. | fromAcceptedOrder, getShipmentNumber, getTransportOrder, getStatus, getNotes, getCustomerAccount, getCargoLoad, getPickupFacility, getDeliveryFacility, isInternational |
| ShipmentRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.shipment. | canBePlanned, canBeDispatched, canBeMarkedInTransit, canBeDelivered, canBeCancelled, requiresSpecialHandling, isCompleted, isTerminal |
| ShipmentStatus | enum | Enum di stato del ciclo di vita. | isTerminal |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
