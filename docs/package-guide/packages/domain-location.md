# `domain/location`

Indirizzi, coordinate geografiche e luoghi fisici.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `Address` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_FIELD_LENGTH, COUNTRY_CODE_LENGTH, street, city, postalCode, countryCode, coordinates, normalizedCountryCode | of, withCoordinates, getStreet, getCity, getPostalCode, getCountryCode, getCoordinates, hasCoordinates |
| `GeoCoordinates` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | latitude, longitude | of, getLatitude, getLongitude, isNorthernHemisphere, isSouthernHemisphere, isEasternHemisphere, isWesternHemisphere, equals |
| `Location` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_NAME_LENGTH, name, address, zoneId | of, getName, getAddress, getZoneId, hasCoordinates, getCoordinates, isInCountry, isInSameTimeZone |
