# Package `domain.order`

Ordini di trasporto: richiesta commerciale prima della spedizione.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| TransportOrder | class | Classe del package domain.order; rappresenta un concetto del modello TruckFlow. | draft, submitted, getOrderNumber, getCustomerAccount, getCargoLoad, getPickupFacility, getDeliveryFacility, getPickupWindow, getDeliveryWindow, getServiceType |
| TransportOrderStatus | enum | Enum di stato del ciclo di vita. | isTerminal |
| TransportServiceType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | supportsTemperatureControl, supportsHazardousMaterial |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
