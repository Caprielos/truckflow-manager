# Package `domain.loadsecurity`

Fissaggio carico: attrezzature, checklist e regole.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| LoadSecuringChecklist | class | Classe del package domain.loadsecurity; rappresenta un concetto del modello TruckFlow. | of, getEquipment, countByType, hasAtLeast |
| LoadSecuringEquipment | class | Classe del package domain.loadsecurity; rappresenta un concetto del modello TruckFlow. | of, getType, getQuantity, getCapacityDan, totalCapacityDan, equals, hashCode |
| LoadSecuringEquipmentType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | - |
| LoadSecuringRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.loadsecurity. | hasMinimumEquipmentForCargo, estimateMinimumStraps |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
