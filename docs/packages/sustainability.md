# Package `sustainability` — Sustainability

## Scopo

Calcola emissioni, rating ambientale e informazioni su carburante/emission standard.

## Concetti principali

- `EmissionEstimate`
- `EmissionRating`
- `EmissionStandard`
- `FuelType`
- `SustainabilityRules`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `EmissionEstimate` | final class | Entity o value object del package. |
| `EmissionRating` | enum | Enum di classificazione/valori ammessi. |
| `EmissionStandard` | enum | Enum di classificazione/valori ammessi. |
| `FuelType` | enum | Enum di classificazione/valori ammessi. |
| `SustainabilityRules` | final class | Classe statica di regole di business del package. |

## Enum e valori ammessi

- `EmissionRating`: `LOW`, `MEDIUM`, `HIGH`, `VERY_HIGH`
- `EmissionStandard`: `EURO_0`, `EURO_1`, `EURO_2`, `EURO_3`, `EURO_4`, `EURO_5`, `EURO_6`, `ZERO_EMISSION`, `UNKNOWN`
- `FuelType`: `DIESEL`, `HVO`, `LNG`, `CNG`, `ELECTRIC`, `HYDROGEN`, `UNKNOWN`

## Regole di business

- Emissioni dipendono da distanza, consumo e fattori emissivi.
- Rating e standard Euro aiutano reporting e scelte operative.

## Collegamenti con altri package

- fleet, fuel, route, reporting

## Test collegati

- `EmissionEstimateTest.java`
- `SustainabilityRulesTest.java`

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
