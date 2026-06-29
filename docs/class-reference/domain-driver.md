# Package `domain.driver`

Autisti, patenti, CQC, ADR, qualifiche operative e stato disponibilità.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| Driver | class | Classe del package domain.driver; rappresenta un concetto del modello TruckFlow. | available, availableWithCertificates, assigned, onLeave, suspended, inactive, getDriverCode, getFullName, getStatus, getLicenseCategories |
| DriverAdrCertificateType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | isSpecialization |
| DriverCertificate | class | Classe del package domain.driver; rappresenta un concetto del modello TruckFlow. | of, getType, getIssuedAt, getExpiresAt, getDocumentNumber, getNotes, isValidOn, expiresWithin, equals, hashCode |
| DriverCertificateType | enum | Enum/tipo di classificazione usato nelle regole di dominio. | - |
| DriverLicenseCategory | enum | Categoria funzionale usata per distinguere casi operativi o contabili. | isHeavyGoodsLicense, isTrailerExtension |
| DriverOperationalQualification | enum | Enum: insieme chiuso di valori ammessi dal dominio. | - |
| DriverProfessionalQualification | enum | Enum: insieme chiuso di valori ammessi dal dominio. | getHarmonizedCode |
| DriverRules | class | Classe di regole per validare e calcolare comportamenti nel package domain.driver. | canBeAssigned, hasRequiredLicenseForVehicleCombination, hasRequiredProfessionalQualificationForGoodsTransport, hasRequiredAdrCertificatesForCargoLoad, hasRequiredOperationalQualificationsForShipment, hasValidProfessionalCertificatesForGoodsTransport, hasValidAdrCertificatesForCargoLoad, hasValidOperationalCertificatesForShipment, canDriveVehicleCombinationOnDate, canDriveVehicleCombination |
| DriverStatus | enum | Enum di stato del ciclo di vita. | canBeAssigned |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
