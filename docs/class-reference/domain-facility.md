# Package `domain.facility`

Strutture fisiche: sedi, depositi, magazzini, piazzali e relativi costi.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| Facility | class | Classe del package domain.facility; rappresenta un concetto del modello TruckFlow. | active, inactive, getCode, getType, getLocation, getOperatingHours, getNotes, isActive, isOpenAt, isInCountry |
| FacilityCostFrequency | enum | Enum: insieme chiuso di valori ammessi dal dominio. | isRecurring |
| FacilityCostLine | class | Riga di dettaglio: rappresenta una voce numerabile/economica/documentale. | of, monthly, yearly, oneTime, getCode, getType, getDescription, getAmount, getFrequency, getCoveragePeriod |
| FacilityCostType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | - |
| FacilityFinancialProfile | class | Profilo che raggruppa informazioni tecniche, operative o economiche. | owned, rented, leased, thirdPartyYard, getFacilityCode, getOwnershipType, getPurchasePrice, getMonthlyRent, getDepositAmount, getCostLines |
| FacilityOwnershipType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | isOwnedAsset, requiresRecurringOccupancyPayment |
| FacilityType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | - |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
