# Package `domain.availability`

Disponibilità di risorse e regole di indisponibilità.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| AvailabilityResourceType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | - |
| AvailabilityRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.availability. | isResourceAvailableForWindow, hasBlockingRecordForWindow, canAddAvailabilityRecord, findRecordsForResource |
| AvailabilityStatus | enum | Enum di stato del ciclo di vita. | isBookable, isBlocking |
| ResourceAvailability | class | Classe del package domain.availability; rappresenta un concetto del modello TruckFlow. | of, available, reserved, assigned, unavailable, maintenance, onLeave, getResourceType, getResourceCode, getDateRange |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
