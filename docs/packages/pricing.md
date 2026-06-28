# Package `pricing` — Pricing e costi

## Scopo

Costi tratta, pedaggi, carburante, usura, surcharge e breakdown prezzo.

## Classi ed enum

| Nome | Tipo | Ruolo sintetico |
|---|---|---|
| `CostEstimationSource` | Enum | Valori controllati usati dalle regole di dominio. |
| `PriceBreakdown` | Classe | Classe di dominio del package. |
| `PricingLine` | Classe | Classe di dominio del package. |
| `PricingLineType` | Enum | Valori controllati usati dalle regole di dominio. |
| `PricingRules` | Classe | Classe di regole di business del package. |
| `RouteCostEstimate` | Classe | Classe di dominio del package. |

## Enum principali

### `CostEstimationSource`

Valori: `MANUAL`, `INTERNAL_MODEL`, `VIAMICHELIN`, `HERE_MAPS`, `PTV`, `GOOGLE_MAPS`, `OTHER_EXTERNAL_PROVIDER`.

### `PricingLineType`

Valori: `BASE_FREIGHT`, `DISTANCE_CHARGE`, `FUEL_SURCHARGE`, `TOLL_CHARGE`, `VEHICLE_WEAR_CHARGE`, `ADR_SURCHARGE`, `TEMPERATURE_CONTROL_SURCHARGE`, `WAITING_TIME_CHARGE`, `HANDLING_CHARGE`, `DISCOUNT`.


## Costi e pricing

Il pricing è costruito a righe:

```text
base freight
distance charge
fuel surcharge
toll charge
vehicle wear
ADR surcharge
temperature surcharge
waiting time
handling
discount
```

`RouteCostEstimate` può rappresentare stime manuali, interne o da provider esterni.


## Collegamenti con altri package

Questo package non vive isolato: le sue classi vengono usate dalle regole di business e dai casi d’uso futuri.

Per capire il flusso completo leggere anche:

- [`../domain-overview.md`](../domain-overview.md)
- [`../domain-rules.md`](../domain-rules.md)
- [`../domain-package-map.md`](../domain-package-map.md)

## Test collegati

I test si trovano sotto:

```text
src/test/java/it/gabriele/truckflow/domain/pricing
```

Quando si modifica questo package, eseguire sempre:

```bash
mvn clean test
```
