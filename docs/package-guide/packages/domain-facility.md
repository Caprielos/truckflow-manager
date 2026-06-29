# `domain/facility`

Strutture aziendali: deposito, sede, magazzino, piazzale, proprietà/affitto e spese.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `Facility` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_CODE_LENGTH, code, type, location, operatingHours, notes, active | active, inactive, getCode, getType, getLocation, getOperatingHours, getNotes, isActive |
| `FacilityCostFrequency` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | isRecurring |
| `FacilityCostLine` | class | Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini. | MAX_CODE_LENGTH, MAX_DESCRIPTION_LENGTH, code, type, description, amount, frequency, coveragePeriod | of, monthly, yearly, oneTime, getCode, getType, getDescription, getAmount |
| `FacilityCostType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | — |
| `FacilityFinancialProfile` | class | Classe economica: modella prezzi, costi, IVA, fatture, ricavi o margini. | MAX_FACILITY_CODE_LENGTH, facilityCode, ownershipType, purchasePrice, monthlyRent, depositAmount, costLines, notes | owned, rented, leased, thirdPartyYard, getFacilityCode, getOwnershipType, getPurchasePrice, getMonthlyRent |
| `FacilityOwnershipType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | isOwnedAsset, requiresRecurringOccupancyPayment |
| `FacilityType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | — |
