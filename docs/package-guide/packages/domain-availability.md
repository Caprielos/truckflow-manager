# `domain/availability`

Disponibilità di risorse: veicoli, driver, rimorchi, strutture o altre risorse operative.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `AvailabilityResourceType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | — |
| `AvailabilityRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | isResourceAvailableForWindow, hasBlockingRecordForWindow, canAddAvailabilityRecord, findRecordsForResource |
| `AvailabilityStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | bookable, blocking | isBookable, isBlocking |
| `ResourceAvailability` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_RESOURCE_CODE_LENGTH, resourceType, resourceCode, dateRange, timeWindow, status, notes | of, available, reserved, assigned, unavailable, maintenance, onLeave, getResourceType |
