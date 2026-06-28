# Package `pricing` — Pricing

## Scopo

Gestisce preventivi, breakdown prezzo, costi tratta, supplementi e sconti.

## Concetti principali

- `RouteCostEstimate`
- `PricingLine`
- `PriceBreakdown`
- `PricingRules`
- `PricingLineType`
- `CostEstimationSource`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `CostEstimationSource` | enum | Enum di classificazione/valori ammessi. |
| `PriceBreakdown` | final class | Entity o value object del package. |
| `PricingLine` | final class | Entity o value object del package. |
| `PricingLineType` | enum | Enum di classificazione/valori ammessi. |
| `PricingRules` | final class | Classe statica di regole di business del package. |
| `RouteCostEstimate` | final class | Entity o value object del package. |

## Enum e valori ammessi

- `CostEstimationSource`: `MANUAL`, `INTERNAL_MODEL`, `VIAMICHELIN`, `HERE_MAPS`, `PTV`, `GOOGLE_MAPS`, `OTHER_EXTERNAL_PROVIDER`
- `PricingLineType`: `BASE_FREIGHT`, `DISTANCE_CHARGE`, `FUEL_SURCHARGE`, `TOLL_CHARGE`, `VEHICLE_WEAR_CHARGE`, `ADR_SURCHARGE`, `TEMPERATURE_CONTROL_SURCHARGE`, `WAITING_TIME_CHARGE`, `HANDLING_CHARGE`, `DISCOUNT`

## Regole di business

- Costi carburante, pedaggi, usura, ADR, frigo, attesa e handling sono linee separate.
- Sconti e surcharge sono distinguibili.
- Fonti esterne di stima strada restano fuori dal domain.

## Collegamenti con altri package

- route, fleet, cargo, fuel, billing

## Test collegati

- `PriceBreakdownTest.java`
- `PricingLineTest.java`
- `PricingRulesTest.java`
- `RouteCostEstimateTest.java`

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
