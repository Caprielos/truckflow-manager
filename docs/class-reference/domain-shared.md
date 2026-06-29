# Domain `shared` spiegato

Value object riutilizzabili: Money, Weight, Distance, Notes, ecc.

## Classi principali

### `DateRange`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `startDate`
- `endDate`

Metodi pubblici principali:

- `of()`
- `getStartDate()`
- `getEndDate()`
- `contains()`
- `overlapsWith()`
- `isInside()`
- `daysInclusive()`
- `equals()`
- `hashCode()`
- `toString()`

### `Dimension`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `CENTIMETERS_PER_METER`
- `lengthMeters`
- `widthMeters`
- `heightMeters`

Metodi pubblici principali:

- `ofMeters()`
- `ofCentimeters()`
- `getLengthMeters()`
- `getWidthMeters()`
- `getHeightMeters()`
- `calculateVolume()`
- `fitsInside()`
- `equals()`
- `hashCode()`
- `toString()`

### `Distance`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `METERS_PER_KILOMETER`
- `kilometers`

Metodi pubblici principali:

- `ofKilometers()`
- `ofMeters()`
- `getKilometers()`
- `isGreaterThan()`
- `isLessThanOrEqualTo()`
- `equals()`
- `hashCode()`
- `toString()`

### `Money`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `amount`
- `currency`

Metodi pubblici principali:

- `of()`
- `getAmount()`
- `getCurrency()`
- `getCurrencyCode()`
- `add()`
- `subtract()`
- `isGreaterThan()`
- `isLessThanOrEqualTo()`
- `equals()`
- `hashCode()`
- `toString()`

### `Notes`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MAX_LENGTH`
- `text`

Metodi pubblici principali:

- `of()`
- `empty()`
- `getText()`
- `isEmpty()`
- `hasText()`
- `contains()`
- `equals()`
- `hashCode()`
- `toString()`

### `Percentage`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `MIN_VALUE`
- `MAX_VALUE`
- `ONE_HUNDRED`
- `value`

Metodi pubblici principali:

- `of()`
- `getValue()`
- `toMultiplier()`
- `isGreaterThan()`
- `isLessThanOrEqualTo()`
- `equals()`
- `hashCode()`
- `toString()`

### `TemperatureRange`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `minCelsius`
- `maxCelsius`

Metodi pubblici principali:

- `ofCelsius()`
- `getMinCelsius()`
- `getMaxCelsius()`
- `contains()`
- `isCoveredBy()`
- `equals()`
- `hashCode()`
- `toString()`

### `TimeWindow`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `startTime`
- `endTime`

Metodi pubblici principali:

- `of()`
- `getStartTime()`
- `getEndTime()`
- `contains()`
- `overlapsWith()`
- `isInside()`
- `equals()`
- `hashCode()`
- `toString()`

### `Volume`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `LITERS_PER_CUBIC_METER`
- `cubicMeters`

Metodi pubblici principali:

- `ofCubicMeters()`
- `ofLiters()`
- `getCubicMeters()`
- `isGreaterThan()`
- `isLessThanOrEqualTo()`
- `equals()`
- `hashCode()`
- `toString()`

### `Weight`

Tipo: `class`.

Classe di dominio/applicazione del package: rappresenta un concetto reale usato dal sistema.

Campi principali:

- `KILOGRAMS_PER_TON`
- `kilograms`

Metodi pubblici principali:

- `ofKilograms()`
- `ofTons()`
- `getKilograms()`
- `isGreaterThan()`
- `isLessThanOrEqualTo()`
- `equals()`
- `hashCode()`
- `toString()`
