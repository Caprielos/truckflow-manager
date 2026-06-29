# Package `economics` — Economia, costi, IVA e marginalità

Centro economico: acquisti asset, IVA, fatture fornitore, costi missione, ricavi, ledger, utile/perdita e debito/cassa negativa.

## Percorso

```text
src/main/java/it/gabriele/truckflow/domain/economics
```

## Classi

- `CustomerRevenueInvoice`
- `EconomicsRules`
- `FinancialBalance`
- `FinancingAgreement`
- `FleetAssetAcquisition`
- `FleetAssetCategory`
- `FleetAssetCostComponent`
- `FleetAssetCostComponentType`
- `FleetAssetPurchase`
- `FleetEconomicLedger`
- `FleetFinancialStatement`
- `InsurancePolicy`
- `MissionCostLine`
- `MissionCostType`
- `MissionEconomics`
- `MissionRevenueLine`
- `MissionRevenueType`
- `ProfitabilityResult`
- `ProfitabilityStatus`
- `PurchaseCategory`
- `PurchaseLine`
- `RecurringExpense`
- `RecurringExpenseCategory`
- `SupplierInvoice`
- `SupplierInvoiceStatus`
- `TaxableRevenueLine`
- `VatBreakdown`
- `VatRate`
- `VatTreatment`

## Test collegati

- `FleetAssetAcquisitionTest`
- `FleetAssetPurchaseTest`
- `FleetEconomicLedgerTest`
- `FleetFinancialStatementTest`
- `MissionEconomicsTest`
- `SupplierInvoiceTest`
- `TaxedSupplierInvoiceTest`
- `VatBreakdownTest`

## Ruolo nel sistema

Questo package contribuisce al domain model e deve restare indipendente da database, controller REST e framework esterni.
