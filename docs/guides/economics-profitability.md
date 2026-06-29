# Economics, IVA e profitability

## Dove si trova

```text
src/main/java/it/gabriele/truckflow/domain/economics
```

## Cosa gestisce

Il package `economics` è il centro economico del progetto.

Gestisce:

- costo camion;
- costo trattore stradale;
- costo rimorchio/semirimorchio;
- costo allestimento;
- costo frigo;
- costo gomme;
- costo telematica;
- assicurazioni;
- finanziamenti;
- fatture fornitori;
- fatture cliente tassabili;
- IVA;
- ricavi missione;
- costi missione;
- ledger aziendale;
- utile/perdita;
- debito/cassa negativa.

## Costo di un camion

Per il singolo bene si usa:

```text
FleetAssetPurchase
```

Per un acquisto composto con più parti si usa:

```text
FleetAssetAcquisition
FleetAssetCostComponent
FleetAssetCostComponentType
VatBreakdown
VatRate
VatTreatment
```

Esempio concettuale:

```text
Acquisto flotta ACQ-001
- trattore stradale: 120.000 € + IVA
- semirimorchio: 42.000 € + IVA
- allestimento frigo: 18.000 € + IVA
- gomme iniziali: 3.600 € + IVA
- telematica: 900 € + IVA
```

## IVA

La documentazione distingue:

```text
imponibile
IVA
lordo
IVA recuperabile
IVA non recuperabile
costo contabile reale
```

Regola importante:

```text
IVA vendite non è ricavo vero
IVA acquisti detraibile non è costo vero
IVA non detraibile entra nel costo reale
```

## Mission economics

Le missioni usano:

```text
MissionRevenueLine
MissionCostLine
MissionEconomics
ProfitabilityResult
ProfitabilityStatus
```

Esempio:

```text
Ricavo cliente: 1.200 €
Costi:
- carburante 310 €
- pedaggi 140 €
- autista 320 €
- gomme/usura 45 €
- manutenzione stimata 35 €
Risultato: +350 €
```

## Fleet financial statement

Per il periodo aziendale:

```text
FleetFinancialStatement
FleetEconomicLedger
FinancialBalance
```

Serve a capire se il periodo è in utile, perdita o cassa negativa.
