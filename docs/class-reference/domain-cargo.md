# Package `domain.cargo`

Merce e carico: categorie, ADR, peso, dimensioni e regole operative.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| AdrClass | enum | Enum: insieme chiuso di valori ammessi dal dominio. | getCode, isExplosives, isGas, isFlammableLiquid, isRadioactive |
| CargoCategory | enum | Categoria funzionale usata per distinguere casi operativi o contabili. | requiresTemperatureControl, isPalletized, requiresAdrData, isLiquid, isBulk, isWaste, requiresSanitaryOrVeterinaryDocuments, requiresFirDocument, requiresEerCode, isOversized |
| CargoItem | class | Classe del package domain.cargo; rappresenta un concetto del modello TruckFlow. | of, temperatureControlled, dangerousGoods, temperatureControlledDangerousGoods, getDescription, getCategory, getWeight, getDimension, getRequiredTemperatureRange, getDangerousGoodsProfile |
| CargoLoad | class | Classe del package domain.cargo; rappresenta un concetto del modello TruckFlow. | of, getItems, getItemCount, calculateTotalWeight, calculateTotalVolume, requiresTemperatureControl, containsDangerousGoods, hasDangerousGoodsProfile, requiresAdrTransport, requiresAdrTankTransport |
| CargoLoadRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.cargo. | isWithinMaxWeight, isWithinMaxVolume, fitsInsideCargoSpace, requiresTemperatureControlledTransport, containsHazardousMaterial, containsDangerousGoods, requiresAdrTransport, requiresAdrTankTransport, containsExplosives, containsRadioactiveMaterial |
| CargoOperationalRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.cargo. | requiredDocumentsFor, requiredVehicleCertificatesFor, requiresEerCode, requiresUnNumber |
| DangerousGoodsProfile | class | Profilo che raggruppa informazioni tecniche, operative o economiche. | of, getUnNumber, getProperShippingName, getAdrClass, getClassificationCode, getPackingGroup, getHazardLabels, getTunnelRestrictionCode, getTransportCategory, requiresTankTransport |
| HazardLabel | enum | Enum: insieme chiuso di valori ammessi dal dominio. | getCode, isGasLabel, isTankRelevantLabel |
| PackingGroup | enum | Enum: insieme chiuso di valori ammessi dal dominio. | getDescription, isHighDanger, isMediumDanger, isLowDanger |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
