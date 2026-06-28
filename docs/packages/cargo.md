# Package `cargo` — Merce e carico

## Scopo

Modella la merce come centro operativo: categoria, peso, volume, temperatura, ADR e documenti richiesti.

## Classi ed enum

| Nome | Tipo | Ruolo sintetico |
|---|---|---|
| `AdrClass` | Enum | Valori controllati usati dalle regole di dominio. |
| `CargoCategory` | Enum | Categoria operativa della merce che attiva vincoli e documenti. |
| `CargoItem` | Classe | Singola merce/carico con peso, dimensioni, categoria e ADR opzionale. |
| `CargoLoad` | Classe | Lista immutabile di merci della spedizione. |
| `CargoLoadRules` | Classe | Regole su peso, volume, temperatura, ADR e dimensioni. |
| `CargoOperationalRules` | Classe | Regole che derivano documenti e certificati richiesti dalla categoria merce. |
| `DangerousGoodsProfile` | Classe | Profilo ADR con numero ONU, classe, packing group e informazioni di sicurezza. |
| `HazardLabel` | Enum | Valori controllati usati dalle regole di dominio. |
| `PackingGroup` | Enum | Valori controllati usati dalle regole di dominio. |

## Enum principali

### `AdrClass`

Valori: `CLASS_1_EXPLOSIVES`, `CLASS_2_GASES`, `CLASS_3_FLAMMABLE_LIQUIDS`, `CLASS_4_1_FLAMMABLE_SOLIDS`, `CLASS_4_2_SPONTANEOUS_COMBUSTION`, `CLASS_4_3_WATER_REACTIVE`, `CLASS_5_1_OXIDIZING_SUBSTANCES`, `CLASS_5_2_ORGANIC_PEROXIDES`, `CLASS_6_1_TOXIC_SUBSTANCES`, `CLASS_6_2_INFECTIOUS_SUBSTANCES`, `CLASS_7_RADIOACTIVE_MATERIAL`, `CLASS_8_CORROSIVE_SUBSTANCES`, `CLASS_9_MISCELLANEOUS`.

### `CargoCategory`

Valori: `GENERAL`, `PALLETIZED_DRY_GOODS`, `FOOD`, `REFRIGERATED_FOOD`, `PHARMACEUTICAL`, `TEMPERATURE_CONTROLLED_GOODS`, `FRAGILE`, `ELECTRONICS`, `HIGH_VALUE_GOODS`, `HAZARDOUS_MATERIAL`, `DANGEROUS_GOODS`, `OVERSIZED`, `MACHINERY`, `VEHICLES`, `CONTAINERIZED_GOODS`, `LIQUID`, `FOOD_GRADE_LIQUID`, `FUEL`, `GAS`, `CONSTRUCTION_MATERIAL`, `BULK_DRY`, `BULK_INERT_GOODS`, `AGRICULTURAL_BULK`, `HAY_BALES`, `COILS`, `CONCRETE`, `WASTE_NON_DANGEROUS`, `WASTE_DANGEROUS`, `LIVESTOCK`.

### `HazardLabel`

Valori: `LABEL_1_EXPLOSIVES`, `LABEL_2_1_FLAMMABLE_GAS`, `LABEL_2_2_NON_FLAMMABLE_GAS`, `LABEL_2_3_TOXIC_GAS`, `LABEL_3_FLAMMABLE_LIQUID`, `LABEL_4_1_FLAMMABLE_SOLID`, `LABEL_4_2_SPONTANEOUS_COMBUSTION`, `LABEL_4_3_DANGEROUS_WHEN_WET`, `LABEL_5_1_OXIDIZER`, `LABEL_5_2_ORGANIC_PEROXIDE`, `LABEL_6_1_TOXIC`, `LABEL_6_2_INFECTIOUS`, `LABEL_7_RADIOACTIVE`, `LABEL_8_CORROSIVE`, `LABEL_9_MISCELLANEOUS`.

### `PackingGroup`

Valori: `I`, `II`, `III`.


## Ruolo centrale della merce

La merce è l’elemento che collega mezzo, autista, azienda, documenti e costi.

Esempi:

```text
PALLETIZED_DRY_GOODS -> capacità EPAL e fissaggio carico
TEMPERATURE_CONTROLLED_GOODS -> ATP + frigo/isotermico + range temperatura
DANGEROUS_GOODS -> ADR, numero ONU, SDS e istruzioni scritte
WASTE_DANGEROUS -> EER/CER + FIR + licenza aziendale rifiuti
LIVESTOCK -> documenti veterinari + abilitazione animali vivi
FOOD_GRADE_LIQUID -> cisterna alimentare/inox + sanificazione
```

## CargoItem e CargoLoad

`CargoItem` rappresenta una riga di merce.
`CargoLoad` rappresenta il carico totale.

Il carico totale calcola peso, volume e requisiti comuni.

## ADR

Il profilo ADR è separato dalla categoria merce:

```text
CargoCategory.DANGEROUS_GOODS
DangerousGoodsProfile con UN number, classe ADR, packing group, tunnel code
```

## Documenti e certificati richiesti

`CargoOperationalRules` decide documenti e certificati richiesti in base alla categoria.


## Collegamenti con altri package

Questo package non vive isolato: le sue classi vengono usate dalle regole di business e dai casi d’uso futuri.

Per capire il flusso completo leggere anche:

- [`../domain-overview.md`](../domain-overview.md)
- [`../domain-rules.md`](../domain-rules.md)
- [`../domain-package-map.md`](../domain-package-map.md)

## Test collegati

I test si trovano sotto:

```text
src/test/java/it/gabriele/truckflow/domain/cargo
```

Quando si modifica questo package, eseguire sempre:

```bash
mvn clean test
```
