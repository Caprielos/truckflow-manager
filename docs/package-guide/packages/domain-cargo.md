# `domain/cargo`

Merce trasportata: categoria, peso, volume, temperatura, ADR e regole operative cargo.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `AdrClass` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | code | getCode, isExplosives, isGas, isFlammableLiquid, isRadioactive |
| `CargoCategory` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | requiresTemperatureControl, palletized, requiresAdrData, liquid, bulk, waste, sanitarySensitive, oversized | requiresTemperatureControl, isPalletized, requiresAdrData, isLiquid, isBulk, isWaste, requiresSanitaryOrVeterinaryDocuments, requiresFirDocument |
| `CargoItem` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_DESCRIPTION_LENGTH, description, category, weight, dimension, requiredTemperatureRange, dangerousGoodsProfile, notes | of, temperatureControlled, dangerousGoods, temperatureControlledDangerousGoods, getDescription, getCategory, getWeight, getDimension |
| `CargoLoad` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | items | of, getItems, getItemCount, calculateTotalWeight, calculateTotalVolume, requiresTemperatureControl, containsDangerousGoods, hasDangerousGoodsProfile |
| `CargoLoadRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | isWithinMaxWeight, isWithinMaxVolume, fitsInsideCargoSpace, requiresTemperatureControlledTransport, containsHazardousMaterial, containsDangerousGoods, requiresAdrTransport, requiresAdrTankTransport |
| `CargoOperationalRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | requiredDocumentsFor, requiredVehicleCertificatesFor, requiresEerCode, requiresUnNumber |
| `DangerousGoodsProfile` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_PROPER_SHIPPING_NAME_LENGTH, MAX_CLASSIFICATION_CODE_LENGTH, MAX_TUNNEL_RESTRICTION_CODE_LENGTH, unNumber, properShippingName, adrClass, classificationCode, packingGroup | of, getUnNumber, getProperShippingName, getAdrClass, getClassificationCode, getPackingGroup, getHazardLabels, getTunnelRestrictionCode |
| `HazardLabel` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | code | getCode, isGasLabel, isTankRelevantLabel |
| `PackingGroup` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | description | getDescription, isHighDanger, isMediumDanger, isLowDanger |
