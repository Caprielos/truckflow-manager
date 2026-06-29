# Package `domain.location`

Indirizzi, coordinate e location con timezone.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| Address | class | Classe del package domain.location; rappresenta un concetto del modello TruckFlow. | of, withCoordinates, getStreet, getCity, getPostalCode, getCountryCode, getCoordinates, hasCoordinates, isInCountry, formatSingleLine |
| GeoCoordinates | class | Classe del package domain.location; rappresenta un concetto del modello TruckFlow. | of, getLatitude, getLongitude, isNorthernHemisphere, isSouthernHemisphere, isEasternHemisphere, isWesternHemisphere, equals, hashCode, toString |
| Location | class | Classe del package domain.location; rappresenta un concetto del modello TruckFlow. | of, getName, getAddress, getZoneId, hasCoordinates, getCoordinates, isInCountry, isInSameTimeZone, formatSingleLine, equals |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
