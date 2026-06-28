# Package `cargo` — Merci e carichi

Modella merce, colli, carichi, categorie cargo, ADR e regole operative derivate dal tipo di merce.

## Responsabilità

- CargoLoad viene usato da Order e quindi da Shipment.
- CargoOperationalRules genera requisiti tecnici/documentali: ADR, ATP, rifiuti, animali vivi, HACCP, fissaggio.

## Classi

- `AdrClass` — enum con valori: `CLASS_1_EXPLOSIVES`, `CLASS_2_GASES`, `CLASS_3_FLAMMABLE_LIQUIDS`, `CLASS_4_1_FLAMMABLE_SOLIDS`, `CLASS_4_2_SPONTANEOUS_COMBUSTION`, `CLASS_4_3_WATER_REACTIVE`, `CLASS_5_1_OXIDIZING_SUBSTANCES`, `CLASS_5_2_ORGANIC_PEROXIDES`, `CLASS_6_1_TOXIC_SUBSTANCES`, `CLASS_6_2_INFECTIOUS_SUBSTANCES`, `CLASS_7_RADIOACTIVE_MATERIAL`, `CLASS_8_CORROSIVE_SUBSTANCES`, `CLASS_9_MISCELLANEOUS`.
- `CargoCategory` — enum con valori: `GENERAL`, `PALLETIZED_DRY_GOODS`, `FOOD`, `REFRIGERATED_FOOD`, `PHARMACEUTICAL`, `TEMPERATURE_CONTROLLED_GOODS`, `FRAGILE`, `ELECTRONICS`, `HIGH_VALUE_GOODS`, `HAZARDOUS_MATERIAL`, `DANGEROUS_GOODS`, `OVERSIZED`, `MACHINERY`, `VEHICLES`, `CONTAINERIZED_GOODS`….
- `CargoItem` — modello/domain object del package.
- `CargoLoad` — modello/domain object del package.
- `CargoLoadRules` — classe di regole pure del package.
- `CargoOperationalRules` — classe di regole pure del package.
- `DangerousGoodsProfile` — modello/domain object del package.
- `HazardLabel` — enum con valori: `LABEL_1_EXPLOSIVES`, `LABEL_2_1_FLAMMABLE_GAS`, `LABEL_2_2_NON_FLAMMABLE_GAS`, `LABEL_2_3_TOXIC_GAS`, `LABEL_3_FLAMMABLE_LIQUID`, `LABEL_4_1_FLAMMABLE_SOLID`, `LABEL_4_2_SPONTANEOUS_COMBUSTION`, `LABEL_4_3_DANGEROUS_WHEN_WET`, `LABEL_5_1_OXIDIZER`, `LABEL_5_2_ORGANIC_PEROXIDE`, `LABEL_6_1_TOXIC`, `LABEL_6_2_INFECTIOUS`, `LABEL_7_RADIOACTIVE`, `LABEL_8_CORROSIVE`, `LABEL_9_MISCELLANEOUS`.
- `PackingGroup` — enum con valori: `I`, `II`, `III`.

## Regole importanti

- La categoria merce determina requisiti operativi e documentali.
- ADR e profilo dangerous goods restano separati dai colli ordinari.

## Collegamenti

- CargoLoad viene usato da Order e quindi da Shipment.
- CargoOperationalRules genera requisiti tecnici/documentali: ADR, ATP, rifiuti, animali vivi, HACCP, fissaggio.
