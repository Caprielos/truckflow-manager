# Package `pricing` — Preventivazione costi

Modella stima costi rotta, breakdown prezzo e righe economiche: base freight, fuel surcharge, pedaggi, ADR, temperatura.

## Responsabilità

- RouteCostEstimate genera costi tecnici.
- PriceBreakdown aggrega righe prezzo per preventivo o fattura.

## Classi

- `CostEstimationSource` — enum con valori: `MANUAL`, `INTERNAL_MODEL`, `VIAMICHELIN`, `HERE_MAPS`, `PTV`, `GOOGLE_MAPS`, `OTHER_EXTERNAL_PROVIDER`.
- `PriceBreakdown` — modello/domain object del package.
- `PricingLine` — modello/domain object del package.
- `PricingLineType` — enum con valori: `BASE_FREIGHT`, `DISTANCE_CHARGE`, `FUEL_SURCHARGE`, `TOLL_CHARGE`, `VEHICLE_WEAR_CHARGE`, `ADR_SURCHARGE`, `TEMPERATURE_CONTROL_SURCHARGE`, `WAITING_TIME_CHARGE`, `HANDLING_CHARGE`, `DISCOUNT`.
- `PricingRules` — classe di regole pure del package.
- `RouteCostEstimate` — modello/domain object del package.

## Collegamenti

- RouteCostEstimate genera costi tecnici.
- PriceBreakdown aggrega righe prezzo per preventivo o fattura.
