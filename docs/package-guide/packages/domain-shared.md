# `domain/shared`

Value object riutilizzabili: Money, Weight, Distance, Notes, ecc.

## Come leggerlo

Questo package contiene concetti o regole del business. Non dovrebbe dipendere da database, web o infrastructure.

## Classi

| Classe | Tipo | Cosa fa | Campi principali | Metodi pubblici principali |
|---|---|---|---|---|
| `DateRange` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | startDate, endDate | of, getStartDate, getEndDate, contains, overlapsWith, isInside, daysInclusive, equals |
| `Dimension` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | CENTIMETERS_PER_METER, lengthMeters, widthMeters, heightMeters | ofMeters, ofCentimeters, getLengthMeters, getWidthMeters, getHeightMeters, calculateVolume, fitsInside, equals |
| `Distance` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | METERS_PER_KILOMETER, kilometers | ofKilometers, ofMeters, getKilometers, isGreaterThan, isLessThanOrEqualTo, equals, hashCode, toString |
| `Money` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | amount, currency | of, getAmount, getCurrency, getCurrencyCode, add, subtract, isGreaterThan, isLessThanOrEqualTo |
| `Notes` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MAX_LENGTH, text | of, empty, getText, isEmpty, hasText, contains, equals, hashCode |
| `Percentage` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | MIN_VALUE, MAX_VALUE, ONE_HUNDRED, value | of, getValue, toMultiplier, isGreaterThan, isLessThanOrEqualTo, equals, hashCode, toString |
| `TemperatureRange` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | minCelsius, maxCelsius | ofCelsius, getMinCelsius, getMaxCelsius, contains, isCoveredBy, equals, hashCode, toString |
| `TimeWindow` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | startTime, endTime | of, getStartTime, getEndTime, contains, overlapsWith, isInside, equals, hashCode |
| `Volume` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | LITERS_PER_CUBIC_METER, cubicMeters | ofCubicMeters, ofLiters, getCubicMeters, isGreaterThan, isLessThanOrEqualTo, equals, hashCode, toString |
| `Weight` | class | Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema. | KILOGRAMS_PER_TON, kilograms | ofKilograms, ofTons, getKilograms, isGreaterThan, isLessThanOrEqualTo, equals, hashCode, toString |
