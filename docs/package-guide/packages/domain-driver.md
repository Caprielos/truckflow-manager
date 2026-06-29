# `domain/driver`

Autisti, patenti, certificati, qualifiche operative e regole abilitative.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `Driver` | class | Classe legata all’autista, alle sue abilitazioni o al suo costo operativo. | MAX_DRIVER_CODE_LENGTH, MAX_FULL_NAME_LENGTH, driverCode, fullName, status, licenseCategories, professionalQualifications, adrCertificates | available, availableWithCertificates, assigned, onLeave, suspended, inactive, getDriverCode, getFullName |
| `DriverAdrCertificateType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | specialization | isSpecialization |
| `DriverCertificate` | class | Classe legata all’autista, alle sue abilitazioni o al suo costo operativo. | type, issuedAt, expiresAt, documentNumber, notes | of, getType, getIssuedAt, getExpiresAt, getDocumentNumber, getNotes, isValidOn, expiresWithin |
| `DriverCertificateType` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | — |
| `DriverLicenseCategory` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | heavyGoodsLicense, trailerExtension | isHeavyGoodsLicense, isTrailerExtension |
| `DriverOperationalQualification` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | — | — |
| `DriverProfessionalQualification` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | harmonizedCode | getHarmonizedCode |
| `DriverRules` | class | Classe di regole: contiene controlli e decisioni di business, senza salvare dati. | — | canBeAssigned, hasRequiredLicenseForVehicleCombination, hasRequiredProfessionalQualificationForGoodsTransport, hasRequiredAdrCertificatesForCargoLoad, hasRequiredOperationalQualificationsForShipment, hasValidProfessionalCertificatesForGoodsTransport, hasValidAdrCertificatesForCargoLoad, hasValidOperationalCertificatesForShipment |
| `DriverStatus` | enum | Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura. | assignable | canBeAssigned |
