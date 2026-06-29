# Domain `driver` spiegato

Autisti, patenti, certificati, qualifiche operative e regole abilitative.

## Classi principali

### `Driver`

Tipo: `class`.

Classe legata all’autista, alle sue abilitazioni o al suo costo operativo.

Campi principali:

- `MAX_DRIVER_CODE_LENGTH`
- `MAX_FULL_NAME_LENGTH`
- `driverCode`
- `fullName`
- `status`
- `licenseCategories`
- `professionalQualifications`
- `adrCertificates`
- `operationalQualifications`
- `certificates`
- `notes`

Metodi pubblici principali:

- `available()`
- `availableWithCertificates()`
- `assigned()`
- `onLeave()`
- `suspended()`
- `inactive()`
- `getDriverCode()`
- `getFullName()`
- `getStatus()`
- `getLicenseCategories()`
- `getProfessionalQualifications()`
- `getAdrCertificates()`

### `DriverAdrCertificateType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `specialization`

Metodi pubblici principali:

- `isSpecialization()`

### `DriverCertificate`

Tipo: `class`.

Classe legata all’autista, alle sue abilitazioni o al suo costo operativo.

Campi principali:

- `type`
- `issuedAt`
- `expiresAt`
- `documentNumber`
- `notes`

Metodi pubblici principali:

- `of()`
- `getType()`
- `getIssuedAt()`
- `getExpiresAt()`
- `getDocumentNumber()`
- `getNotes()`
- `isValidOn()`
- `expiresWithin()`
- `equals()`
- `hashCode()`

### `DriverCertificateType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

### `DriverLicenseCategory`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `heavyGoodsLicense`
- `trailerExtension`

Metodi pubblici principali:

- `isHeavyGoodsLicense()`
- `isTrailerExtension()`

### `DriverOperationalQualification`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

### `DriverProfessionalQualification`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `harmonizedCode`

Metodi pubblici principali:

- `getHarmonizedCode()`

### `DriverRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `canBeAssigned()`
- `hasRequiredLicenseForVehicleCombination()`
- `hasRequiredProfessionalQualificationForGoodsTransport()`
- `hasRequiredAdrCertificatesForCargoLoad()`
- `hasRequiredOperationalQualificationsForShipment()`
- `hasValidProfessionalCertificatesForGoodsTransport()`
- `hasValidAdrCertificatesForCargoLoad()`
- `hasValidOperationalCertificatesForShipment()`
- `canDriveVehicleCombinationOnDate()`
- `canDriveVehicleCombination()`
- `canBeAssignedToShipment()`
- `canBeAssignedToShipmentOnDate()`

### `DriverStatus`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

Campi principali:

- `assignable`

Metodi pubblici principali:

- `canBeAssigned()`
