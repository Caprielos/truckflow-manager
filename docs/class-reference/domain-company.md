# Domain `company` spiegato

Azienda di trasporto, licenze aziendali e autorizzazioni operative.

## Classi principali

### `CompanyComplianceRules`

Tipo: `class`.

Classe di regole: contiene controlli e decisioni di business, senza salvare dati.

Metodi pubblici principali:

- `hasBaseRoadHaulageAuthorizations()`
- `canOperateInternationalTransport()`
- `canTransportCargo()`

### `CompanyLicense`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `type`
- `expiresAt`
- `notes`

Metodi pubblici principali:

- `of()`
- `getType()`
- `getExpiresAt()`
- `getNotes()`
- `isValidOn()`
- `equals()`
- `hashCode()`

### `CompanyLicenseType`

Tipo: `enum`.

Enum: elenco chiuso di valori ammessi per evitare stringhe libere e errori di battitura.

### `TransportCompany`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_CODE_LENGTH`
- `MAX_NAME_LENGTH`
- `companyCode`
- `businessName`
- `vatNumber`
- `licenses`
- `notes`

Metodi pubblici principali:

- `of()`
- `getCompanyCode()`
- `getBusinessName()`
- `getVatNumber()`
- `getLicenses()`
- `getNotes()`
- `hasLicense()`
- `hasValidLicense()`
- `hasAnyValidEnvironmentalLicense()`
