# Package `domain.shared`

Value object riutilizzabili: denaro, peso, distanza, volume, dimensioni, percentuali, finestre temporali e note.

## Classi

| Classe | Tipo | Cosa rappresenta/fa | Metodi principali |
| --- | --- | --- | --- |
| DateRange | class | Classe del package domain.shared; rappresenta un concetto del modello TruckFlow. | of, getStartDate, getEndDate, contains, overlapsWith, isInside, daysInclusive, equals, hashCode, toString |
| Dimension | class | Classe del package domain.shared; rappresenta un concetto del modello TruckFlow. | ofMeters, ofCentimeters, getLengthMeters, getWidthMeters, getHeightMeters, calculateVolume, fitsInside, equals, hashCode, toString |
| Distance | class | Classe del package domain.shared; rappresenta un concetto del modello TruckFlow. | ofKilometers, ofMeters, getKilometers, isGreaterThan, isLessThanOrEqualTo, equals, hashCode, toString |
| Money | class | Classe del package domain.shared; rappresenta un concetto del modello TruckFlow. | of, getAmount, getCurrency, getCurrencyCode, add, subtract, isGreaterThan, isLessThanOrEqualTo, equals, hashCode |
| Notes | class | Classe del package domain.shared; rappresenta un concetto del modello TruckFlow. | of, empty, getText, isEmpty, hasText, contains, equals, hashCode, toString |
| Percentage | class | Classe del package domain.shared; rappresenta un concetto del modello TruckFlow. | of, getValue, toMultiplier, isGreaterThan, isLessThanOrEqualTo, equals, hashCode, toString |
| TemperatureRange | class | Classe del package domain.shared; rappresenta un concetto del modello TruckFlow. | ofCelsius, getMinCelsius, getMaxCelsius, contains, isCoveredBy, equals, hashCode, toString |
| TimeWindow | class | Classe del package domain.shared; rappresenta un concetto del modello TruckFlow. | of, getStartTime, getEndTime, contains, overlapsWith, isInside, equals, hashCode, toString |
| Volume | class | Classe del package domain.shared; rappresenta un concetto del modello TruckFlow. | ofCubicMeters, ofLiters, getCubicMeters, isGreaterThan, isLessThanOrEqualTo, equals, hashCode, toString |
| Weight | class | Classe del package domain.shared; rappresenta un concetto del modello TruckFlow. | ofKilograms, ofTons, getKilograms, isGreaterThan, isLessThanOrEqualTo, equals, hashCode, toString |

## Come ragionare su questo package

Questo package contiene concetti del dominio. Le classi non dovrebbero dipendere da database o controller web. Le regole dovrebbero rimanere testabili con JUnit.
