# `domain/loadsecurity`

Fissaggio carico, attrezzature e checklist sicurezza carico.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `LoadSecuringChecklist` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | equipment | of, getEquipment, countByType, hasAtLeast |
| `LoadSecuringEquipment` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | type, quantity, capacityDan | of, getType, getQuantity, getCapacityDan, totalCapacityDan, equals, hashCode |
| `LoadSecuringEquipmentType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | — |
| `LoadSecuringRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | hasMinimumEquipmentForCargo, estimateMinimumStraps |
