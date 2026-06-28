# Package `shared` — Shared

## Scopo

Contiene value object riutilizzabili e puri come peso, distanza, denaro, volume, dimensioni e finestre temporali.

## Concetti principali

- `Weight`
- `Distance`
- `Volume`
- `Dimension`
- `Money`
- `Percentage`
- `TemperatureRange`
- `TimeWindow`
- `DateRange`
- `Notes`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `DateRange` | final class | Entity o value object del package. |
| `Dimension` | final class | Entity o value object del package. |
| `Distance` | final class | Entity o value object del package. |
| `Money` | final class | Entity o value object del package. |
| `Notes` | final class | Entity o value object del package. |
| `Percentage` | final class | Entity o value object del package. |
| `TemperatureRange` | final class | Entity o value object del package. |
| `TimeWindow` | final class | Entity o value object del package. |
| `Volume` | final class | Entity o value object del package. |
| `Weight` | final class | Entity o value object del package. |

## Enum e valori ammessi

_Nessuna enum nel package._

## Regole di business

- Value object validano unità, valori negativi, NaN/infinito e coerenza.
- Sono immutabili e riutilizzati da tutti i package.

## Collegamenti con altri package

- tutti i package domain

## Test collegati

- `DateRangeTest.java`
- `DimensionTest.java`
- `DistanceTest.java`
- `MoneyTest.java`
- `NotesTest.java`
- `PercentageTest.java`
- `TemperatureRangeTest.java`
- `TimeWindowTest.java`
- `VolumeTest.java`
- `WeightTest.java`

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
