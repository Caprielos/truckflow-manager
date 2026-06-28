# Package `location` — Location

## Scopo

Gestisce indirizzi, coordinate geografiche e fusi orari.

## Concetti principali

- `Address`
- `GeoCoordinates`
- `Location`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `Address` | final class | Entity o value object del package. |
| `GeoCoordinates` | final class | Entity o value object del package. |
| `Location` | final class | Entity o value object del package. |

## Enum e valori ammessi

_Nessuna enum nel package._

## Regole di business

- Coordinate devono essere valide.
- Una location può includere timezone per finestre temporali future.

## Collegamenti con altri package

- route, facility, tracking, order, shipment

## Test collegati

- `AddressTest.java`
- `GeoCoordinatesTest.java`
- `LocationTest.java`

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
