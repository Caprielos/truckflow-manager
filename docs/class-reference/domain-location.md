# Domain `location` spiegato

Indirizzi, coordinate geografiche e luoghi fisici.

## Classi principali

### `Address`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_FIELD_LENGTH`
- `COUNTRY_CODE_LENGTH`
- `street`
- `city`
- `postalCode`
- `countryCode`
- `coordinates`
- `normalizedCountryCode`

Metodi pubblici principali:

- `of()`
- `withCoordinates()`
- `getStreet()`
- `getCity()`
- `getPostalCode()`
- `getCountryCode()`
- `getCoordinates()`
- `hasCoordinates()`
- `isInCountry()`
- `formatSingleLine()`
- `equals()`
- `hashCode()`

### `GeoCoordinates`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `latitude`
- `longitude`

Metodi pubblici principali:

- `of()`
- `getLatitude()`
- `getLongitude()`
- `isNorthernHemisphere()`
- `isSouthernHemisphere()`
- `isEasternHemisphere()`
- `isWesternHemisphere()`
- `equals()`
- `hashCode()`
- `toString()`

### `Location`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_NAME_LENGTH`
- `name`
- `address`
- `zoneId`

Metodi pubblici principali:

- `of()`
- `getName()`
- `getAddress()`
- `getZoneId()`
- `hasCoordinates()`
- `getCoordinates()`
- `isInCountry()`
- `isInSameTimeZone()`
- `formatSingleLine()`
- `equals()`
- `hashCode()`
- `toString()`
