# Package `order` — Order

## Scopo

Gestisce la richiesta commerciale del cliente prima che diventi spedizione/missione.

## Concetti principali

- `TransportOrder`
- `TransportOrderStatus`
- `TransportServiceType`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `TransportOrder` | final class | Richiesta commerciale del cliente. |
| `TransportOrderStatus` | enum | Enum di classificazione/valori ammessi. |
| `TransportServiceType` | enum | Enum di classificazione/valori ammessi. |

## Enum e valori ammessi

- `TransportOrderStatus`: `DRAFT`, `SUBMITTED`, `ACCEPTED`, `REJECTED`, `CANCELLED`
- `TransportServiceType`: `STANDARD`, `EXPRESS`, `REFRIGERATED`, `HAZARDOUS`, `OVERSIZED`

## Regole di business

- Ordine passa da bozza/inviato/accettato/rifiutato/cancellato secondo transizioni coerenti.
- Un ordine accettato può generare una spedizione.

## Collegamenti con altri package

- customer, cargo, location, shipment, pricing

## Test collegati

- `TransportOrderTest.java`

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
