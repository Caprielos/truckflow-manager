# Package `pricing` — Preventivi e prezzo cliente

Gestisce prezzo da proporre/fatturare al cliente, voci prezzo e breakdown commerciale.

## Percorso

```text
src/main/java/it/gabriele/truckflow/domain/pricing
```

## Classi

- `CostEstimationSource`
- `PriceBreakdown`
- `PricingLine`
- `PricingLineType`
- `PricingRules`
- `RouteCostEstimate`

## Test collegati

- `PriceBreakdownTest`
- `PricingLineTest`
- `PricingRulesTest`
- `RouteCostEstimateTest`

## Ruolo nel sistema

Questo package contribuisce al domain model e deve restare indipendente da database, controller REST e framework esterni.
