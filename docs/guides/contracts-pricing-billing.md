# Contract, pricing e billing

## Contract

Package:

```text
src/main/java/it/gabriele/truckflow/domain/contract
```

Rappresenta contratti cliente, listini e regole tariffarie.

Classi principali:

```text
CustomerContract
ContractRateCard
TariffRule
TariffRuleType
ChargeUnit
CustomerContractRules
```

Esempi di regole tariffarie:

- tariffa al km;
- tariffa per tratta;
- tariffa per pallet;
- supplemento carburante;
- supplemento ADR;
- supplemento frigo;
- notturno;
- festivo;
- attesa;
- urgenza.

## Pricing

Package:

```text
src/main/java/it/gabriele/truckflow/domain/pricing
```

Serve per costruire il prezzo cliente/preventivo.

Classi principali:

```text
PriceBreakdown
PricingLine
PricingLineType
RouteCostEstimate
PricingRules
```

## Billing

Package:

```text
src/main/java/it/gabriele/truckflow/domain/billing
```

Serve per fattura cliente e pagamenti.

Classi principali:

```text
Invoice
PaymentRecord
BillingRules
```

## Differenza importante

```text
contract = come si decide il prezzo in base all'accordo col cliente
pricing = prezzo/preventivo calcolato
billing = fattura e incasso
economics = confronto tra ricavi, costi, IVA e margine
```
