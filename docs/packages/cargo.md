# Package `cargo` — Cargo

## Scopo

Modella la merce, che è il centro delle regole: determina allestimento, documenti, requisiti autista, certificati mezzo e costi.

## Concetti principali

- `CargoCategory`
- `CargoItem`
- `CargoLoad`
- `DangerousGoodsProfile`
- `AdrClass`
- `PackingGroup`
- `HazardLabel`
- `CargoLoadRules`
- `CargoOperationalRules`

## Classi del package

| Classe | Tipo | Ruolo sintetico |
|---|---|---|
| `AdrClass` | enum | Enum di classificazione/valori ammessi. |
| `CargoCategory` | enum | Enum di classificazione/valori ammessi. |
| `CargoItem` | final class | Singolo collo/articolo di carico. |
| `CargoLoad` | final class | Insieme di articoli caricati in una richiesta o missione. |
| `CargoLoadRules` | final class | Regole su peso, volume, temperatura e ADR del carico. |
| `CargoOperationalRules` | final class | Regole operative che trasformano categoria merce in requisiti. |
| `DangerousGoodsProfile` | final class | Profilo ADR della merce pericolosa. |
| `HazardLabel` | enum | Enum di classificazione/valori ammessi. |
| `PackingGroup` | enum | Enum di classificazione/valori ammessi. |

## Enum e valori ammessi

- `AdrClass`: `CLASS_1_EXPLOSIVES`, `CLASS_2_GASES`, `CLASS_3_FLAMMABLE_LIQUIDS`, `CLASS_4_1_FLAMMABLE_SOLIDS`, `CLASS_4_2_SPONTANEOUS_COMBUSTION`, `CLASS_4_3_WATER_REACTIVE`, `CLASS_5_1_OXIDIZING_SUBSTANCES`, `CLASS_5_2_ORGANIC_PEROXIDES`, `CLASS_6_1_TOXIC_SUBSTANCES`, `CLASS_6_2_INFECTIOUS_SUBSTANCES`, `CLASS_7_RADIOACTIVE_MATERIAL`, `CLASS_8_CORROSIVE_SUBSTANCES`, `CLASS_9_MISCELLANEOUS`
- `CargoCategory`: `GENERAL`, `PALLETIZED_DRY_GOODS`, `FOOD`, `REFRIGERATED_FOOD`, `PHARMACEUTICAL`, `TEMPERATURE_CONTROLLED_GOODS`, `FRAGILE`, `ELECTRONICS`, `HIGH_VALUE_GOODS`, `HAZARDOUS_MATERIAL`, `DANGEROUS_GOODS`, `OVERSIZED`, `MACHINERY`, `VEHICLES`, `CONTAINERIZED_GOODS`, `LIQUID`, `FOOD_GRADE_LIQUID`, `FUEL`, `GAS`, `CONSTRUCTION_MATERIAL`, `BULK_DRY`, `BULK_INERT_GOODS`, `AGRICULTURAL_BULK`, `HAY_BALES`, `COILS`, `CONCRETE`, `WASTE_NON_DANGEROUS`, `WASTE_DANGEROUS`, `LIVESTOCK`
- `HazardLabel`: `LABEL_1_EXPLOSIVES`, `LABEL_2_1_FLAMMABLE_GAS`, `LABEL_2_2_NON_FLAMMABLE_GAS`, `LABEL_2_3_TOXIC_GAS`, `LABEL_3_FLAMMABLE_LIQUID`, `LABEL_4_1_FLAMMABLE_SOLID`, `LABEL_4_2_SPONTANEOUS_COMBUSTION`, `LABEL_4_3_DANGEROUS_WHEN_WET`, `LABEL_5_1_OXIDIZER`, `LABEL_5_2_ORGANIC_PEROXIDE`, `LABEL_6_1_TOXIC`, `LABEL_6_2_INFECTIOUS`, `LABEL_7_RADIOACTIVE`, `LABEL_8_CORROSIVE`, `LABEL_9_MISCELLANEOUS`
- `PackingGroup`: `II`, `III`

## Regole di business

- Il carico calcola peso e volume complessivo.
- La merce refrigerata richiede temperatura e allestimento/certificati coerenti.
- La merce ADR richiede profilo ADR, autista e veicolo idonei.
- Rifiuti, animali vivi, liquidi alimentari e merci sfuse attivano requisiti specifici.

## Collegamenti con altri package

- fleet per compatibilità corpo mezzo
- driver per patenti/ADR/qualifiche
- document per documenti obbligatori
- company per licenze azienda
- pricing per supplementi

## Test collegati

- `CargoItemTest.java`
- `CargoLoadRulesTest.java`
- `CargoLoadTest.java`
- `CargoOperationalRulesTest.java`
- `DangerousCargoTest.java`
- `DangerousGoodsProfileTest.java`

## Note di progettazione

Questo package appartiene al domain puro. Non deve contenere codice di database, API esterne, controller web, query SQL, repository concreti o logica di framework.

Le regole devono rimanere testabili con JUnit senza avviare servizi esterni.
