# Package `contract` — Contratti cliente e listini

Gestisce contratti, rate card, tariff rules, supplementi e logiche tariffarie realistiche.

## Percorso

```text
src/main/java/it/gabriele/truckflow/domain/contract
```

## Classi

- `ChargeUnit`
- `ContractRateCard`
- `CustomerContract`
- `CustomerContractRules`
- `TariffRule`
- `TariffRuleType`

## Test collegati

- `CustomerContractModelTest`

## Ruolo nel sistema

Questo package contribuisce al domain model e deve restare indipendente da database, controller REST e framework esterni.
